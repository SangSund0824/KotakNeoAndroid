package com.example.feature.marketstream

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.db.StockEntity
import com.example.core.data.repository.StockRepository
import com.example.core.network.datasource.OrderDataSource
import com.example.core.network.model.order.OrderRequest
import com.example.core.network.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OrderState(
    val isLoading: Boolean = false,
    val success: String? = null,
    val error: String? = null,
    val scripLoading: Boolean = false,
    val searchResults: List<StockEntity> = emptyList()
)

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderDataSource: OrderDataSource,
    private val stockRepository: StockRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(OrderState())
    val state: StateFlow<OrderState> = _state.asStateFlow()

    init {
        loadScripMaster()
    }

    private fun loadScripMaster() {
        viewModelScope.launch {
            _state.value = _state.value.copy(scripLoading = true)
            try {
                stockRepository.loadStocksIfNeeded()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Failed to load stocks: ${e.message}")
            } finally {
                _state.value = _state.value.copy(scripLoading = false)
            }
        }
    }

    fun searchStocks(query: String) {
        viewModelScope.launch {
            val results = stockRepository.search(query)
            _state.value = _state.value.copy(searchResults = results)
        }
    }

    fun clearSearch() {
        _state.value = _state.value.copy(searchResults = emptyList())
    }

    fun placeOrder(
        tradingSymbol: String,
        quantity: Int,
        orderType: String,
        price: Double,
        transactionType: String
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, success = null, error = null)
            try {
                val response = orderDataSource.placeOrder(
                    OrderRequest(
                        base_url = sessionManager.baseUrl,
                        trading_token = sessionManager.token,
                        trading_sid = sessionManager.sid,
                        trading_symbol = tradingSymbol,
                        quantity = quantity,
                        order_type = orderType,
                        price = price,
                        transaction_type = transactionType
                    )
                )
                _state.value = _state.value.copy(isLoading = false, success = response.toString())
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Order failed")
            }
        }
    }

    fun resetState() {
        _state.value = OrderState()
    }
}
