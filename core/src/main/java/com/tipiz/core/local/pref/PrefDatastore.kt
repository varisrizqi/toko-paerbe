package com.tipiz.core.local.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.tipiz.core.utils.Constant.PrefDatastore.key_refresh_token
import com.tipiz.core.utils.Constant.PrefDatastore.key_splash_screen
import com.tipiz.core.utils.Constant.PrefDatastore.key_username
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefDatastore(private val dataStore: DataStore<Preferences>) : PrefDataStoreHelper {


    override suspend fun setOnBoarding(value: Boolean) {
        dataStore.edit { pref ->
            pref[key_splash_screen] = value
        }
    }

    override fun getOnBoarding(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            pref[key_splash_screen] ?: false
        }
    }

    suspend fun setRefreshToken(value: String) {
        dataStore.edit { pref ->
            pref[key_refresh_token] = value
        }
    }

    fun getRefreshToken(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[key_refresh_token] ?: ""
        }
    }

    suspend fun setUserName(value: String) {
        dataStore.edit { pref ->
            pref[key_username] = value
        }
    }

    fun getUserName(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[key_username] ?: "false"
        }
    }
}