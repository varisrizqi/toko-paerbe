package com.tipiz.core.utils.state

sealed class SplashState<out R> {
    data object OnBoarding: SplashState<Nothing>()
    data object Dashboard: SplashState<Nothing>()
    data object Login: SplashState<Nothing>()
    data object Profile: SplashState<Nothing>()
}


fun <T>SplashState <T>.runOnboarding(
    execute: () -> Unit
): SplashState<T> =apply {
    if (this is SplashState.OnBoarding){
        println("Condition OnBoarding")
        execute()
    }
}

fun <T>SplashState <T>.runDashBoard(
    execute: () -> Unit
): SplashState<T> =apply {
    if (this is SplashState.Dashboard){
        println("Condition Main")
        execute()
    }
}

fun <T>SplashState <T>.runLogin(
    execute: () -> Unit
): SplashState<T> =apply {
    if (this is SplashState.Login){
        println("Condition Login")
        execute()
    }
}

fun <T>SplashState <T>.runProfile(
    execute: () -> Unit
): SplashState<T> =apply {
    if (this is SplashState.Profile){
        println("Condition Profile")
        execute()
    }
}
