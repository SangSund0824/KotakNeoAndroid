package com.example.core.network.session

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    var baseUrl: String = ""
        private set
    var token: String = ""
        private set
    var sid: String = ""
        private set

    fun setSession(baseUrl: String, token: String, sid: String) {
        this.baseUrl = baseUrl
        this.token = token
        this.sid = sid
    }

    fun clear() {
        baseUrl = ""
        token = ""
        sid = ""
    }
}
