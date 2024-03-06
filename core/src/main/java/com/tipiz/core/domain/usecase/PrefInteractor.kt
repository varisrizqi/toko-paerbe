package com.tipiz.core.domain.usecase

import com.tipiz.core.domain.repository.prefrepo.PrefRepository
import kotlinx.coroutines.flow.Flow

class PrefInteractor(
    private val onBoard : PrefRepository
) : PrefUseCase{
    override suspend fun setOnBoarding(value: Boolean) {
        onBoard.setOnBoarding(value)
    }

    override fun getOnBoarding(): Flow<Boolean> {
        return onBoard.getOnBoarding()
    }
}