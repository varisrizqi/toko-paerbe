package com.tipiz.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.tipiz.core.utils.Constant.PrefDatastore.key_access_token
import com.tipiz.core.utils.Constant.PrefDatastore.key_refresh_token
import com.tipiz.core.utils.Constant.PrefDatastore.key_splash_screen
import com.tipiz.core.utils.Constant.PrefDatastore.key_userid
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

    override suspend fun setAccessToken(value: String) {
        dataStore.edit { pref ->
            pref[key_access_token] = value
        }
    }

    override fun getAccessToken(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[key_access_token] ?: ""
        }
    }

    override suspend fun setUserid(value: String) {
        dataStore.edit { pref->
            pref[key_userid] = value
        }
    }

    override fun getUserid(): Flow<String> {
        return dataStore.data.map {pref->
            pref[key_userid] ?:""
        }
    }

    override suspend fun setRefreshToken(value: String) {
        dataStore.edit { pref ->
            pref[key_refresh_token] = value
        }
    }

    override fun getRefreshToken(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[key_refresh_token] ?: ""
        }
    }

    override suspend fun setUserName(value: String) {
        dataStore.edit { pref ->
            pref[key_username] = value
        }
    }

    override fun getUserName(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[key_username] ?: ""
        }
    }

    override suspend fun clearSession() {
            dataStore.edit {
                it.remove(key_username)
                it.remove(key_access_token)
                it.remove(key_refresh_token)
            }

    }

}