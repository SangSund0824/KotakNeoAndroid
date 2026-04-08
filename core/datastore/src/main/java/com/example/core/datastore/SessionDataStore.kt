package com.example.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

@Singleton
class SessionDataStore @Inject constructor(
    private val context: Context
) {
    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
        val SID = stringPreferencesKey("sid")
        val BASE_URL = stringPreferencesKey("base_url")
        val UCC = stringPreferencesKey("ucc")
        val GREETING_NAME = stringPreferencesKey("greeting_name")
    }

    suspend fun saveSession(
        token: String, 
        sid: String, 
        baseUrl: String, 
        ucc: String? = null, 
        greetingName: String? = null
    ) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = token
            preferences[PreferencesKeys.SID] = sid
            preferences[PreferencesKeys.BASE_URL] = baseUrl
            ucc?.let { preferences[PreferencesKeys.UCC] = it }
            greetingName?.let { preferences[PreferencesKeys.GREETING_NAME] = it }
        }
    }

    fun getSession(): Flow<SessionData?> {
        return context.dataStore.data.map { preferences ->
            val token = preferences[PreferencesKeys.TOKEN]
            val sid = preferences[PreferencesKeys.SID]
            val baseUrl = preferences[PreferencesKeys.BASE_URL]
            
            if (token != null && sid != null && baseUrl != null) {
                SessionData(token, sid, baseUrl)
            } else null
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.TOKEN)
            preferences.remove(PreferencesKeys.SID)
            preferences.remove(PreferencesKeys.BASE_URL)
        }
    }
}

data class SessionData(
    val token: String,
    val sid: String,
    val baseUrl: String
)