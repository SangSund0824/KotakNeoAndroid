package com.example.feature.positions.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.positions.CalculatedPosition
import com.example.feature.positions.PositionsViewModel

@Composable
fun PositionsScreen(
    modifier: Modifier = Modifier,
    viewModel: PositionsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.error!!, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(onClick = { viewModel.loadPositions() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            state.positions.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "No open positions",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text("Positions", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        PnlSummaryCard(
                            totalPnl = state.totalPnl,
                            positionCount = state.positions.size
                        )
                    }
                    items(state.positions) { pos ->
                        PositionCard(pos)
                    }
                }
            }
        }
    }
}

@Composable
private fun PnlSummaryCard(totalPnl: Double, positionCount: Int) {
    val isProfit = totalPnl >= 0
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Total P&L", style = MaterialTheme.typography.labelMedium)
                Text(
                    "%s₹%.2f".format(if (isProfit) "+" else "", totalPnl),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isProfit) MaterialTheme.colorScheme.tertiary
                           else MaterialTheme.colorScheme.error
                )
            }
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surface
            ) {
                Text(
                    "$positionCount position(s)",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun PositionCard(calc: CalculatedPosition) {
    val p = calc.position
    val isProfit = calc.pnl >= 0
    val isBuy = when {
        p.trnsTp?.uppercase()?.trim() == "B" -> true
        p.trnsTp?.uppercase()?.trim() == "S" -> false
        calc.netQty > 0 -> true
        calc.netQty < 0 -> false
        calc.totalBuyQty > 0 -> true
        else -> false
    }
    val isClosed = calc.netQty == 0
    val precisionFormat = "%.${calc.precision}f"

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Row 1: Symbol + P&L
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            p.trdSym ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = MaterialTheme.shapes.extraSmall,
                            color = if (isBuy) MaterialTheme.colorScheme.tertiaryContainer
                                   else MaterialTheme.colorScheme.errorContainer
                        ) {
                            Text(
                                if (isBuy) "BUY" else "SELL",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        "${p.exSeg ?: ""} · ${p.prod ?: ""} · ${p.series ?: ""}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "P&L",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "%s₹${precisionFormat}".format(if (isProfit) "+" else "", calc.pnl),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isProfit) MaterialTheme.colorScheme.tertiary
                               else MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // Row 2: Avg Price + LTP
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelValue("Avg Price", "₹${precisionFormat}".format(calc.avgPrice))
                LabelValue(
                    "LTP",
                    "₹${precisionFormat}".format(calc.ltp)
                )
                LabelValue(
                    "Order Type",
                    when (p.prcTp) {
                        "L" -> "Limit"
                        "MKT" -> "Market"
                        "SL" -> "Stop Loss"
                        else -> p.prcTp ?: ""
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 3: Quantities
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelValue("Buy Qty", "${calc.totalBuyQty}")
                LabelValue("Sell Qty", "${calc.totalSellQty}")
                LabelValue("Net Qty", "${calc.netQty}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 4: Amounts + Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelValue("Buy Amt", "₹%.2f".format(calc.totalBuyAmt))
                LabelValue("Sell Amt", "₹%.2f".format(calc.totalSellAmt))
                LabelValue("Filled", p.flDt ?: "")
            }
        }
    }
}

@Composable
private fun LabelValue(label: String, value: String) {
    Column {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
