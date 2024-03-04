package com.tipiz.core.utils.state

sealed class SplashState<out R> {
    data object OnBoarding: SplashState<Nothing>()
    data object Dashboard: SplashState<Nothing>()
    data object Login: SplashState<Nothing>()
    data object Profile: SplashState<Nothing>()
}
