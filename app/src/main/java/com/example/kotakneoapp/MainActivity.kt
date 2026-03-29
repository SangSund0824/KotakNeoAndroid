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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.feature.login.AuthFlowScreen
import com.example.feature.login.AuthState
import com.example.feature.marketstream.ui.MarketStreamScreen
import com.example.feature.marketstream.ui.PortfolioScreen
import com.example.feature.trade.ui.PlaceOrderScreen
import com.example.kotakneoapp.ui.theme.KotakNeoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.login.LoginViewModel

enum class BottomTab { HOME, TRADE, PORTFOLIO }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotakNeoAppTheme {
                var authData by remember { mutableStateOf<Triple<String, String, String>?>(null) }
                var selectedTab by remember { mutableStateOf(BottomTab.HOME) }

                if (authData == null) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AuthFlowScreen(
                            onAuthSuccess = { },
                            modifier = Modifier.padding(innerPadding)
                        )

                        val vm: LoginViewModel = hiltViewModel()
                        val state = vm.authState.collectAsState().value
                        if (state is AuthState.Authenticated) {
                            authData = Triple(state.baseUrl, state.token, state.sid)
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
                                    selected = selectedTab == BottomTab.PORTFOLIO,
                                    onClick = { selectedTab = BottomTab.PORTFOLIO },
                                    icon = { Icon(Icons.Default.AccountBalance, contentDescription = "Portfolio") },
                                    label = { Text("Portfolio") }
                                )
                            }
                        }
                    ) { innerPadding ->
                        val (baseUrl, token, sid) = authData!!
                        when (selectedTab) {
                            BottomTab.HOME -> MarketStreamScreen(
                                baseUrl = baseUrl,
                                token = token,
                                sid = sid,
                                modifier = Modifier.padding(innerPadding)
                            )
                            BottomTab.TRADE -> PlaceOrderScreen(
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
