package com.tipiz.core.local.pref

import kotlinx.coroutines.flow.Flow

interface PrefDataStoreHelper {
    suspend fun setOnBoarding(value:Boolean)
     fun getOnBoarding(): Flow<Boolean>
}