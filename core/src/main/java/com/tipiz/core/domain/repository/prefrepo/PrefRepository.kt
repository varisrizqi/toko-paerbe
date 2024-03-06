package com.tipiz.core.domain.repository.prefrepo

import kotlinx.coroutines.flow.Flow

interface PrefRepository {

    suspend fun setOnBoarding(value:Boolean)
     fun getOnBoarding(): Flow<Boolean>

//    fun dataSession(): DataSession
}