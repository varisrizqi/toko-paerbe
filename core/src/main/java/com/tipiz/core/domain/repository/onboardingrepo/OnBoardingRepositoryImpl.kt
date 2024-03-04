package com.tipiz.core.domain.repository.onboardingrepo

import com.tipiz.core.local.datasource.DataStoreDataSource
import kotlinx.coroutines.flow.Flow

class OnBoardingRepositoryImpl ( private val dataStore: DataStoreDataSource):OnBoardingRepository{
    override suspend fun setOnBoarding(value: Boolean) {
        dataStore.setOnBoarding(value)
    }

    override suspend fun getOnBoarding(): Flow<Boolean> {
       return dataStore.getOnBoarding()
    }

//    override fun dataSession(): DataSession {
//        val name = dataStore.getProfileName()
//        val accessToken = dataStore.getAccessToken()
//        val onBoardingState = dataStore.getOnBoardingState()
//        val triple: Triple<String, String, Boolean> = Triple(name, accessToken, onBoardingState)
//        return triple.toUIData()
//    }


}