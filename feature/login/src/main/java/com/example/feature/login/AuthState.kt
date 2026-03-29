package com.example.feature.login

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class LoginSuccess(val sid: String, val token: String) : AuthState()
    data class Authenticated(val sid: String, val token: String, val baseUrl: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
