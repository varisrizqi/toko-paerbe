package com.tipiz.core.utils

import com.tipiz.core.domain.model.login.DataLogin
import com.tipiz.core.domain.model.login.DataSession
import com.tipiz.core.domain.model.login.DataToken
import com.tipiz.core.network.data.login.LoginResponse
import com.tipiz.core.network.data.refresh.RefreshResponse
import com.tipiz.core.network.data.register.RegisterResponse
import com.tipiz.core.utils.state.SplashState

object DataMapper {

    fun RegisterResponse.toUiData() = DataToken(
        accessToken = data.accessToken,
        expiresAt = data.expiresAt,
        refreshToken = data.refreshToken
    )

    fun LoginResponse.toUiData() = DataLogin(
        userImage = data.userImage,
        userName = data.userName,
        accessToken = data.accessToken,
        expiresAt = data.expiresAt,
        refreshToken = data.refreshToken
    )

    fun RefreshResponse.toUiData() = DataToken(
        accessToken = data.accessToken,
        expiresAt = data.expiresAt,
        refreshToken = data.refreshToken
    )


    fun Triple<String, String, Boolean>.toUiData() = DataSession(
        userName = this.first,
        accessToken = this.second,
        onBoardingState = this.third
    )

    fun DataSession.toSplashState() = when {
        this.userName.isEmpty() && this.accessToken.isNotEmpty() -> {
            SplashState.Profile
        }

        this.userName.isNotEmpty() && this.accessToken.isNotEmpty() -> {
            SplashState.Dashboard
        }

        this.onBoardingState -> {
            SplashState.Login
        }

        else -> {
            SplashState.OnBoarding
        }
    }
}