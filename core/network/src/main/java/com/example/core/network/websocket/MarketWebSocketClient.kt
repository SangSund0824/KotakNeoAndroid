package com.example.core.network.websocket

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

data class MarketTick(
    val symbol: String,
    val price: Double,
    val timestamp: Long = System.currentTimeMillis()
)

@Singleton
class MarketWebSocketClient @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private var webSocket: WebSocket? = null
    private val _marketTicks = Channel<MarketTick>(Channel.UNLIMITED)
    val marketTicks: Flow<MarketTick> = _marketTicks.receiveAsFlow()
    
    private var baseUrl: String = ""
    private var token: String = ""
    private var sid: String = ""

    fun connect(baseUrl: String, token: String, sid: String) {
        this.baseUrl = baseUrl
        this.token = token
        this.sid = sid
        
        // Connect to YOUR backend WebSocket, not Kotak directly
        val backendWsUrl = "ws://ec2-16-171-55-251.eu-north-1.compute.amazonaws.com/ws/stream"
        val request = Request.Builder()
            .url(backendWsUrl)
            .build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("WebSocket connected to backend")
                // Send connection details to backend
                val initMessage = """
                    {
                        "base_url": "$baseUrl",
                        "token": "$token",
                        "sid": "$sid",
                        "symbols": []
                    }
                """.trimIndent()
                webSocket.send(initMessage)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val parts = text.split(",")
                    if (parts.size >= 2) {
                        val symbol = parts[0].substringAfter("\"symbol\":\"").substringBefore("\"")
                        val priceStr = parts[1].substringAfter("\"price\":").substringBefore("}")
                        val price = priceStr.toDoubleOrNull() ?: 0.0
                        
                        _marketTicks.trySend(MarketTick(symbol, price))
                    }
                } catch (e: Exception) {
                    println("Error parsing market tick: ${e.message}")
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("WebSocket error: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("WebSocket closed: $reason")
            }
        })
    }

    fun subscribeToSymbols(symbols: List<String>) {
        // Send updated symbols list to backend
        val message = """
            {
                "base_url": "${this.baseUrl}",
                "token": "${this.token}",
                "sid": "${this.sid}",
                "symbols": [${symbols.joinToString(",") { "\"$it\"" }}]
            }
        """.trimIndent()
        webSocket?.send(message)
    }

    fun disconnect() {
        webSocket?.close(1000, "Client disconnect")
        webSocket = null
    }
}