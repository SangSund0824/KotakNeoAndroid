package com.example.core.network.websocket

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

data class OrderUpdate(
    val raw: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Singleton
class OrderFeedWebSocketClient @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private var webSocket: WebSocket? = null
    private val _updates = MutableSharedFlow<OrderUpdate>(extraBufferCapacity = 64)
    val updates: SharedFlow<OrderUpdate> = _updates.asSharedFlow()

    companion object {
        private const val WS_URL = "wss://ec2-16-170-188-211.eu-north-1.compute.amazonaws.com/ws/orderfeed"
    }

    fun connect() {
        val request = Request.Builder().url(WS_URL).build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("OrderFeed WebSocket connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                _updates.tryEmit(OrderUpdate(text))
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("OrderFeed error: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("OrderFeed closed: $reason")
            }
        })
    }

    fun disconnect() {
        webSocket?.close(1000, "Client disconnect")
        webSocket = null
    }
}
