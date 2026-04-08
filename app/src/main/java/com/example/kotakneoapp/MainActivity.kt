package com.example.kotakneoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.feature.login.AuthFlowScreen
import com.example.feature.login.AuthState
import com.example.feature.portfolio.ui.PortfolioScreen
import com.example.feature.trade.ui.PlaceOrderScreen
import com.example.kotakneoapp.ui.theme.KotakNeoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.login.LoginViewModel

enum class BottomTab { TRADE, PORTFOLIO }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotakNeoAppTheme {
                var isAuthenticated by remember { mutableStateOf(false) }
                var selectedTab by remember { mutableStateOf(BottomTab.TRADE) }

                if (!isAuthenticated) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        val vm: LoginViewModel = hiltViewModel()
                        val state = vm.authState.collectAsState().value
                        
                        when (state) {
                            is AuthState.CheckingSession -> {
                                // Show loading while checking session
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            is AuthState.Authenticated -> {
                                isAuthenticated = true
                            }
                            else -> {
                                AuthFlowScreen(
                                    onAuthSuccess = { },
                                    modifier = Modifier.padding(innerPadding)
                                )
                                
                                if (state is AuthState.Authenticated) {
                                    isAuthenticated = true
                                }
                            }
                        }
                    }
                } else {
                    val vm: LoginViewModel = hiltViewModel()
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = { Text("Kotak Neo") },
                                actions = {
                                    IconButton(onClick = { 
                                        vm.logout()
                                        isAuthenticated = false 
                                    }) {
                                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            NavigationBar {
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
                        when (selectedTab) {
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
