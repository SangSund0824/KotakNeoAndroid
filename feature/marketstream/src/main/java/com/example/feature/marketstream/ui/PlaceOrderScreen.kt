package com.example.feature.marketstream.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.marketstream.OrderViewModel

@Composable
fun PlaceOrderScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var tradingSymbol by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    var price by remember { mutableStateOf("0") }
    var isBuy by remember { mutableStateOf(true) }
    var isMarketOrder by remember { mutableStateOf(true) }
    var showDropdown by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Place Order", style = MaterialTheme.typography.headlineMedium)

            if (state.scripLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                Text("Loading stocks...", style = MaterialTheme.typography.bodySmall)
            }

            // Stock search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it.uppercase()
                    viewModel.searchStocks(it)
                    showDropdown = it.length >= 2
                    if (it.isEmpty()) tradingSymbol = ""
                },
                label = { Text("Search Stock") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                supportingText = {
                    if (tradingSymbol.isNotBlank()) {
                        Text("Selected: $tradingSymbol", color = MaterialTheme.colorScheme.primary)
                    }
                }
            )

            // Search results dropdown
            if (showDropdown && state.searchResults.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    LazyColumn {
                        items(state.searchResults) { stock ->
                            ListItem(
                                headlineContent = {
                                    Text(stock.pTrdSymbol)
                                },
                                supportingContent = {
                                    Text(stock.pSymbolName ?: "", style = MaterialTheme.typography.bodySmall)
                                },
                                modifier = Modifier.clickable {
                                    tradingSymbol = stock.pSymbolName ?: stock.pTrdSymbol
                                    searchQuery = stock.pTrdSymbol
                                    showDropdown = false
                                    viewModel.clearSearch()
                                }
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it.filter { c -> c.isDigit() } },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = isMarketOrder,
                    onClick = { isMarketOrder = true; price = "0" },
                    label = { Text("Market (MKT)") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = !isMarketOrder,
                    onClick = { isMarketOrder = false },
                    label = { Text("Limit (L)") },
                    modifier = Modifier.weight(1f)
                )
            }

            if (!isMarketOrder) {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Limit Price") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = isBuy,
                    onClick = { isBuy = true },
                    label = { Text("BUY") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = !isBuy,
                    onClick = { isBuy = false },
                    label = { Text("SELL") },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = {
                    viewModel.placeOrder(
                        tradingSymbol = tradingSymbol,
                        quantity = quantity.toIntOrNull() ?: 1,
                        orderType = if (isMarketOrder) "MKT" else "L",
                        price = price.toDoubleOrNull() ?: 0.0,
                        transactionType = if (isBuy) "B" else "S"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = tradingSymbol.isNotBlank() && !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text(if (isBuy) "Place Buy Order" else "Place Sell Order")
                }
            }

            state.success?.let {
                Text(it, color = MaterialTheme.colorScheme.primary)
            }
            state.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
