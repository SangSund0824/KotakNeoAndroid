package com.example.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature.login.ui.LoginScreen
import com.example.feature.login.ui.MPINScreen

@Composable
fun AuthFlowScreen(
    onAuthSuccess: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsState()

    when (val state = authState) {
        is AuthState.Idle, is AuthState.Error -> {
            LoginScreen(
                onLoginClick = { mobile, ucc, totp ->
                    viewModel.login(mobile, ucc, totp)
                },
                isLoading = false,
                errorMessage = if (state is AuthState.Error) state.message else null,
                modifier = modifier
            )
        }

        is AuthState.Loading -> {
            LoginScreen(
                onLoginClick = { _, _, _ -> },
                isLoading = true,
                errorMessage = null,
                modifier = modifier
            )
        }

        is AuthState.LoginSuccess -> {
            MPINScreen(
                onVerifyClick = { mpin ->
                    viewModel.verifyMPIN(mpin, state.sid, state.token)
                },
                isLoading = false,
                errorMessage = null,
                modifier = modifier
            )
        }

        is AuthState.Authenticated -> {
            // Trigger callback with baseUrl
            onAuthSuccess(state.baseUrl)
        }
    }
}
