package com.example.feature.marketstream

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.model.market.QuoteResponse
import com.example.core.network.websocket.MarketWebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MarketStreamState(
    val quotes: Map<String, QuoteResponse> = emptyMap(),
    val error: String? = null
)

@HiltViewModel
class MarketStreamViewModel @Inject constructor(
    private val webSocketClient: MarketWebSocketClient
) : ViewModel() {

    private val _state = MutableStateFlow(MarketStreamState())
    val state: StateFlow<MarketStreamState> = _state.asStateFlow()

    init {
        webSocketClient.connect()
        viewModelScope.launch {
            webSocketClient.ticks.collect { tick ->
                val currentQuotes = _state.value.quotes.toMutableMap()
                currentQuotes[tick.symbol] = QuoteResponse(tick.symbol, tick.ltp)
                _state.value = _state.value.copy(quotes = currentQuotes)
            }
        }
    }
}
