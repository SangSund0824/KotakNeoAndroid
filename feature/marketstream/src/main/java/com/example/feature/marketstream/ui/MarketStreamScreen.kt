package com.example.feature.marketstream.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.marketstream.MarketStreamViewModel

@Composable
fun MarketStreamScreen(
    baseUrl: String,
    token: String,
    sid: String,
    modifier: Modifier = Modifier,
    viewModel: MarketStreamViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var symbolInput by remember { mutableStateOf("") }
    val symbols = remember { mutableStateListOf("RELIANCE", "TCS", "INFY") }

    LaunchedEffect(Unit) {
        viewModel.initialize(baseUrl, token, sid)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Market Live Stream",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Symbol input
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = symbolInput,
                    onValueChange = { symbolInput = it.uppercase() },
                    label = { Text("Add Symbol") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (symbolInput.isNotBlank() && !symbols.contains(symbolInput)) {
                            symbols.add(symbolInput)
                            symbolInput = ""
                        }
                    }
                ) {
                    Text("Add")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stream control
            Button(
                onClick = {
                    if (state.isStreaming) {
                        viewModel.stopStreaming()
                    } else {
                        viewModel.startStreaming(symbols)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = if (state.isStreaming) {
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                } else {
                    ButtonDefaults.buttonColors()
                }
            ) {
                Text(if (state.isStreaming) "Stop Streaming" else "Start Streaming")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Quotes list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(symbols) { symbol ->
                    QuoteCard(
                        symbol = symbol,
                        quote = state.quotes[symbol],
                        onRemove = { symbols.remove(symbol) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuoteCard(
    symbol: String,
    quote: com.example.core.network.model.market.QuoteResponse?,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = symbol,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                if (quote != null) {
                    Text(
                        text = if (quote.price != 0.0) "₹${quote.price}" else "Connecting...",
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (quote.price != 0.0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = onRemove) {
                Text("✕", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}
