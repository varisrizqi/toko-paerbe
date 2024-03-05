package com.tipiz.core.local.datasource

import com.tipiz.core.local.pref.PrefDataStoreHelper
import kotlinx.coroutines.flow.Flow

class DataStoreDataSource(private val dataSource: PrefDataStoreHelper) {

    suspend fun setOnBoarding(value:Boolean){
        dataSource.setOnBoarding(value)
    }

     fun getOnBoarding(): Flow<Boolean> {
        return dataSource.getOnBoarding()
    }


}