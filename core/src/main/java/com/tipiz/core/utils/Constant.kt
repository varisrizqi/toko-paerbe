package com.tipiz.core.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {

    object PrefDatastore {
        val key_refresh_token = stringPreferencesKey("key_refresh_token")
        val key_splash_screen = booleanPreferencesKey("key_splashScreen")
        val key_username = stringPreferencesKey("key_username")
        val key_access_token = stringPreferencesKey("key_access_token")
        const val PREFS_NAME = "myPrefs"
        const val KEY_THEME = "key_theme"
        const val KEY_LOCALIZE = "key_LOCALIZE"
        const val KEY_EN = "en"
        const val KEY_IN = "in"
        const val product_table = "product_table"
        const val cart_table = "cart_table"
        const val wishlist_table = "wishlist_table"
        const val paging_key = "paging_key"
        const val INITIAL_PAGE_INDEX = 1
        const val key_wishlist = "key_wishlist"
        const val key_userid = "key_userid"
    }
}