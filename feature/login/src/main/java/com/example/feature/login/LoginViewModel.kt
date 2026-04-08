package com.example.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.datasource.AuthNetworkDataSource
import com.example.core.network.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.CheckingSession)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch {
            _authState.value = AuthState.CheckingSession
            val hasSession = sessionManager.loadSession()
            if (hasSession && sessionManager.isSessionValid()) {
                _authState.value = AuthState.Authenticated(
                    sid = sessionManager.sid,
                    token = sessionManager.token,
                    baseUrl = sessionManager.baseUrl
                )
            } else {
                _authState.value = AuthState.Idle
            }
        }
    }

    fun login(mobileNumber: String, ucc: String, totp: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authNetworkDataSource.login(mobileNumber, ucc, totp)
                val sid = response.data?.sid
                val token = response.data?.token
                if (sid != null && token != null) {
                    _authState.value = AuthState.LoginSuccess(sid = sid, token = token)
                } else {
                    _authState.value = AuthState.Error("Login response missing token/sid")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun verifyMPIN(mpin: String, sid: String, token: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = authNetworkDataSource.verifyMPIN(mpin, sid, token)
                val rToken = response.data?.token
                val rSid = response.data?.sid
                val rBaseUrl = response.data?.baseUrl
                if (rToken != null && rSid != null && rBaseUrl != null) {
                    sessionManager.setSession(baseUrl = rBaseUrl, token = rToken, sid = rSid)
                    _authState.value = AuthState.Authenticated(sid = rSid, token = rToken, baseUrl = rBaseUrl)
                } else {
                    _authState.value = AuthState.Error("MPIN response missing required fields")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "MPIN verification failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clear()
            _authState.value = AuthState.Idle
        }
    }

    fun resetError() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Idle
        }
    }
}
