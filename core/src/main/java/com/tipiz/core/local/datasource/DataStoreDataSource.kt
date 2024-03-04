package com.tipiz.core.local.datasource

import com.tipiz.core.local.pref.PrefDatastore
import kotlinx.coroutines.flow.Flow

class DataStoreDataSource(private val dataSource: PrefDatastore) {

    suspend fun setOnBoarding(value:Boolean){
        dataSource.setOnBoarding(value)
    }

    fun getOnBoarding(): Flow<Boolean> {
        return dataSource.getOnBoarding()
    }


}