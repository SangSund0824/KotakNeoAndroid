package com.example.feature.positions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.datasource.PositionsDataSource
import com.example.core.network.model.portfolio.Position
import com.example.core.network.websocket.MarketWebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CalculatedPosition(
    val position: Position,
    val totalBuyQty: Int,
    val totalSellQty: Int,
    val netQty: Int,
    val carryFwdQty: Int,
    val totalBuyAmt: Double,
    val totalSellAmt: Double,
    val avgPrice: Double,
    val ltp: Double,
    val pnl: Double,
    val precision: Int
)

data class PositionsState(
    val isLoading: Boolean = false,
    val positions: List<CalculatedPosition> = emptyList(),
    val totalPnl: Double = 0.0,
    val error: String? = null
)

@HiltViewModel
class PositionsViewModel @Inject constructor(
    private val positionsDataSource: PositionsDataSource,
    private val marketWebSocketClient: MarketWebSocketClient
) : ViewModel() {

    private val _state = MutableStateFlow(PositionsState())
    val state: StateFlow<PositionsState> = _state.asStateFlow()

    private var rawPositions: List<Position> = emptyList()
    private val ltpMap = mutableMapOf<String, Double>()
    private val pnlMap = mutableMapOf<String, Double>()

    init {
        loadPositions()
        collectTicks()
    }

    private fun collectTicks() {
        viewModelScope.launch {
            marketWebSocketClient.ticks.collect { tick ->
                ltpMap[tick.symbol] = tick.ltp
                tick.pnl?.let { pnlMap[tick.symbol] = it }
                if (rawPositions.isNotEmpty()) {
                    recalculate()
                }
            }
        }
    }

    fun loadPositions() {
        viewModelScope.launch {
            _state.value = PositionsState(isLoading = true)
            try {
                val response = positionsDataSource.getPositions()
                rawPositions = response.data ?: emptyList()

                // Connect to marketfeed — backend auto-subscribes based on positions
                marketWebSocketClient.connect()

                recalculate()
            } catch (e: Exception) {
                _state.value = PositionsState(error = e.message ?: "Failed to load positions")
            }
        }
    }

    private fun recalculate() {
        val calculated = rawPositions.map { calculate(it) }
        val totalPnl = calculated.sumOf { it.pnl }
        _state.value = PositionsState(positions = calculated, totalPnl = totalPnl)
    }

    private fun calculate(p: Position): CalculatedPosition {
        val lotSz = p.lotSz?.toIntOrNull() ?: 1
        val multiplier = p.multiplier?.toDoubleOrNull() ?: 1.0
        val genNum = p.genNum?.toDoubleOrNull() ?: 1.0
        val genDen = p.genDen?.toDoubleOrNull() ?: 1.0
        val prcNum = p.prcNum?.toDoubleOrNull() ?: 1.0
        val prcDen = p.prcDen?.toDoubleOrNull() ?: 1.0
        val precision = p.precision?.toIntOrNull() ?: 2
        val isFnO = p.exSeg?.contains("fo", ignoreCase = true) == true
        val factor = multiplier * (genNum / genDen) * (prcNum / prcDen)

        val ltp = ltpMap[p.trdSym]
            ?: p.ltp?.toDoubleOrNull()
            ?: p.avgPrc?.toDoubleOrNull()
            ?: 0.0

        val hasAggregatedData = p.cfBuyQty != null || p.flBuyQty != null ||
                                p.cfSellQty != null || p.flSellQty != null

        val totalBuyQty: Int
        val totalSellQty: Int
        val totalBuyAmt: Double
        val totalSellAmt: Double

        if (hasAggregatedData) {
            val cfBuyQty = if (isFnO) (p.cfBuyQty ?: 0) / lotSz else (p.cfBuyQty ?: 0)
            val cfSellQty = if (isFnO) (p.cfSellQty ?: 0) / lotSz else (p.cfSellQty ?: 0)
            val flBuyQty = if (isFnO) (p.flBuyQty ?: 0) / lotSz else (p.flBuyQty ?: 0)
            val flSellQty = if (isFnO) (p.flSellQty ?: 0) / lotSz else (p.flSellQty ?: 0)

            totalBuyQty = cfBuyQty + flBuyQty
            totalSellQty = cfSellQty + flSellQty
            totalBuyAmt = (p.cfBuyAmt ?: 0.0) + (p.buyAmt ?: 0.0)
            totalSellAmt = (p.cfSellAmt ?: 0.0) + (p.sellAmt ?: 0.0)
        } else {
            val filledQty = p.fldQty ?: 0
            val avgPrc = p.avgPrc?.toDoubleOrNull() ?: 0.0
            val fillAmt = filledQty * avgPrc * factor
            val isBuy = p.trnsTp?.uppercase()?.trim() == "B"

            totalBuyQty = if (isBuy) filledQty else 0
            totalSellQty = if (!isBuy) filledQty else 0
            totalBuyAmt = if (isBuy) fillAmt else 0.0
            totalSellAmt = if (!isBuy) fillAmt else 0.0
        }

        val netQty = totalBuyQty - totalSellQty
        val carryFwdQty = (p.cfBuyQty ?: 0) - (p.cfSellQty ?: 0)

        val avgPrice = when {
            totalBuyQty > totalSellQty && totalBuyQty > 0 ->
                totalBuyAmt / (totalBuyQty * factor)
            totalBuyQty < totalSellQty && totalSellQty > 0 ->
                totalSellAmt / (totalSellQty * factor)
            else -> 0.0
        }

        // Use backend-calculated PnL if available, otherwise calculate locally
        val pnl = pnlMap[p.trdSym]
            ?: ((totalSellAmt - totalBuyAmt) + (netQty * ltp * factor))

        return CalculatedPosition(
            position = p,
            totalBuyQty = totalBuyQty,
            totalSellQty = totalSellQty,
            netQty = netQty,
            carryFwdQty = carryFwdQty,
            totalBuyAmt = totalBuyAmt,
            totalSellAmt = totalSellAmt,
            avgPrice = avgPrice,
            ltp = ltp,
            pnl = pnl,
            precision = precision
        )
    }

    override fun onCleared() {
        super.onCleared()
        // Don't disconnect — other features may be using the same WebSocket
    }
}
