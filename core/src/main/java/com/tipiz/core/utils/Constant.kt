package com.tipiz.core.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {

    object PrefDatastore {
        val key_refresh_token = stringPreferencesKey("key_refresh_token")
        val key_splash_screen = booleanPreferencesKey("key_splashScreen")
        val key_username = stringPreferencesKey("key_username")
        val key_access_token = stringPreferencesKey("key_access_token")
        val key_userid = stringPreferencesKey("key_userid")
        val key_theme = booleanPreferencesKey("key_theme")
        val key_localize = stringPreferencesKey("key_localize")
        const val PREFS_NAME = "myPrefs"


    }

    //room
    const val paging_key = "paging_key"
    const val product_table = "product_table"
    const val favorite_table = "favorite_table"
    const val INITIAL_PAGE_INDEX = 1
}