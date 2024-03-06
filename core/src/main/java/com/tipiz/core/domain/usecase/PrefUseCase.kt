package com.tipiz.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface PrefUseCase {

    suspend fun setOnBoarding(value:Boolean)
     fun getOnBoarding(): Flow<Boolean>
}