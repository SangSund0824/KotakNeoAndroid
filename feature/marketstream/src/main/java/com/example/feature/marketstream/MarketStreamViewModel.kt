package com.example.feature.marketstream

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.api.KotakBackendApi
import com.example.core.network.model.market.QuoteResponse
import com.example.core.network.websocket.MarketWebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MarketStreamState(
    val isStreaming: Boolean = false,
    val quotes: Map<String, QuoteResponse> = emptyMap(),
    val error: String? = null
)

@HiltViewModel
class MarketStreamViewModel @Inject constructor(
    private val api: KotakBackendApi,
    private val webSocketClient: MarketWebSocketClient
) : ViewModel() {

    private val _state = MutableStateFlow(MarketStreamState())
    val state: StateFlow<MarketStreamState> = _state.asStateFlow()

    private var baseUrl: String = ""
    private var token: String = ""
    private var sid: String = ""

    fun initialize(baseUrl: String, token: String, sid: String) {
        this.baseUrl = baseUrl
        this.token = token
        this.sid = sid

        webSocketClient.connect(baseUrl, token, sid)

        viewModelScope.launch {
            webSocketClient.marketTicks.collect { tick ->
                val currentQuotes = _state.value.quotes.toMutableMap()
                currentQuotes[tick.symbol] = QuoteResponse(tick.symbol, tick.price)
                _state.value = _state.value.copy(quotes = currentQuotes)
            }
        }
    }

    fun startStreaming(symbols: List<String>) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isStreaming = true, 
                    error = null,
                    quotes = symbols.associateWith { 
                        QuoteResponse(it, 0.0) 
                    }
                )
                
                // Send symbols to backend via WebSocket
                webSocketClient.subscribeToSymbols(symbols)

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isStreaming = false,
                    error = e.message ?: "Failed to start streaming"
                )
            }
        }
    }

    fun stopStreaming() {
        _state.value = _state.value.copy(isStreaming = false)
        webSocketClient.disconnect()
    }

    override fun onCleared() {
        super.onCleared()
        stopStreaming()
    }
}
