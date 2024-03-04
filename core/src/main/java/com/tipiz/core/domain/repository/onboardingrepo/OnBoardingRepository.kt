package com.tipiz.core.domain.repository.onboardingrepo

import com.tipiz.core.domain.model.DataSession
import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {

    suspend fun setOnBoarding(value:Boolean)
    suspend fun getOnBoarding(): Flow<Boolean>

//    fun dataSession(): DataSession
}