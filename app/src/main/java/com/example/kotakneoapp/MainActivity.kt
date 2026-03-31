package com.example.kotakneoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.feature.login.AuthFlowScreen
import com.example.feature.login.AuthState
import com.example.feature.marketstream.ui.MarketStreamScreen
import com.example.feature.portfolio.ui.PortfolioScreen
import com.example.feature.positions.ui.PositionsScreen
import com.example.feature.trade.ui.PlaceOrderScreen
import com.example.kotakneoapp.ui.theme.KotakNeoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.login.LoginViewModel

enum class BottomTab { HOME, TRADE, POSITIONS, PORTFOLIO }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotakNeoAppTheme {
                var isAuthenticated by remember { mutableStateOf(false) }
                var selectedTab by remember { mutableStateOf(BottomTab.HOME) }

                if (!isAuthenticated) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AuthFlowScreen(
                            onAuthSuccess = { },
                            modifier = Modifier.padding(innerPadding)
                        )

                        val vm: LoginViewModel = hiltViewModel()
                        val state = vm.authState.collectAsState().value
                        if (state is AuthState.Authenticated) {
                            isAuthenticated = true
                        }
                    }
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = selectedTab == BottomTab.HOME,
                                    onClick = { selectedTab = BottomTab.HOME },
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                    label = { Text("Home") }
                                )
                                NavigationBarItem(
                                    selected = selectedTab == BottomTab.TRADE,
                                    onClick = { selectedTab = BottomTab.TRADE },
                                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Trade") },
                                    label = { Text("Trade") }
                                )
                                NavigationBarItem(
                                    selected = selectedTab == BottomTab.POSITIONS,
                                    onClick = { selectedTab = BottomTab.POSITIONS },
                                    icon = { Icon(Icons.Default.TrendingUp, contentDescription = "Positions") },
                                    label = { Text("Positions") }
                                )
                                NavigationBarItem(
                                    selected = selectedTab == BottomTab.PORTFOLIO,
                                    onClick = { selectedTab = BottomTab.PORTFOLIO },
                                    icon = { Icon(Icons.Default.AccountBalance, contentDescription = "Portfolio") },
                                    label = { Text("Portfolio") }
                                )
                            }
                        }
                    ) { innerPadding ->
                        when (selectedTab) {
                            BottomTab.HOME -> MarketStreamScreen(
                                modifier = Modifier.padding(innerPadding)
                            )
                            BottomTab.TRADE -> PlaceOrderScreen(
                                modifier = Modifier.padding(innerPadding)
                            )
                            BottomTab.POSITIONS -> PositionsScreen(
                                modifier = Modifier.padding(innerPadding)
                            )
                            BottomTab.PORTFOLIO -> PortfolioScreen(
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
