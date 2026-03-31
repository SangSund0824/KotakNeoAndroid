package com.example.core.network.websocket

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.*
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

data class MarketTick(
    val symbol: String,
    val ltp: Double,
    val pnl: Double? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Singleton
class MarketWebSocketClient @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private var webSocket: WebSocket? = null
    private val _ticks = MutableSharedFlow<MarketTick>(extraBufferCapacity = 256)
    val ticks: SharedFlow<MarketTick> = _ticks.asSharedFlow()

    private var isConnected = false

    companion object {
        private const val WS_URL = "wss://ec2-16-170-188-211.eu-north-1.compute.amazonaws.com/ws/marketfeed"
    }

    fun connect() {
        if (isConnected) return
        val request = Request.Builder().url(WS_URL).build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                isConnected = true
                println("MarketFeed WebSocket connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    // Backend sends: {"symbol": "IDEA-EQ", "ltp": 9.45, "pnl": 0.06}
                    val json = JSONObject(text)
                    val symbol = json.optString("symbol", json.optString("ts", ""))
                    val ltp = json.optDouble("ltp", json.optDouble("price", 0.0))
                    val pnl = if (json.has("pnl")) json.optDouble("pnl") else null

                    if (symbol.isNotBlank() && ltp > 0) {
                        _ticks.tryEmit(MarketTick(symbol, ltp, pnl))
                    }
                } catch (e: Exception) {
                    println("MarketFeed parse error: ${e.message}")
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                isConnected = false
                println("MarketFeed error: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                isConnected = false
                println("MarketFeed closed: $reason")
            }
        })
    }

    fun disconnect() {
        isConnected = false
        webSocket?.close(1000, "Client disconnect")
        webSocket = null
    }
}
