package com.example.core.network.session

import com.example.core.datastore.SessionDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val sessionDataStore: SessionDataStore
) {
    var baseUrl: String = ""
        private set
    var token: String = ""
        private set
    var sid: String = ""
        private set

    suspend fun setSession(baseUrl: String, token: String, sid: String) {
        this.baseUrl = baseUrl
        this.token = token
        this.sid = sid
        sessionDataStore.saveSession(token, sid, baseUrl)
    }

    suspend fun loadSession(): Boolean {
        val sessionData = sessionDataStore.getSession().first()
        return if (sessionData != null) {
            this.baseUrl = sessionData.baseUrl
            this.token = sessionData.token
            this.sid = sessionData.sid
            true
        } else {
            false
        }
    }

    fun getSessionFlow() = sessionDataStore.getSession()

    suspend fun clear() {
        baseUrl = ""
        token = ""
        sid = ""
        sessionDataStore.clearSession()
    }

    fun isSessionValid(): Boolean {
        return token.isNotEmpty() && sid.isNotEmpty() && baseUrl.isNotEmpty()
    }
}
