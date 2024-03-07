package com.tipiz.core.utils.state

sealed class UiState<out R> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()

    data class Error(val error: Throwable) : UiState<Nothing>()

    data object Empty : UiState<Nothing>()
}


fun <T> UiState<T>.onSuccess(
    execute: (data: T) -> Unit
): UiState<T> = apply {
    if (this is UiState.Success) {
        println("Condition UiState" + data.toString())
        execute(data)
    }
}

fun <T> UiState<T>.onError(
    execute: (error: Throwable) -> Unit
): UiState<T> = apply {
    if (this is UiState.Error) {
        println("Condition UiState" + error.message)
        execute(error)
    }
}

fun <T> UiState<T>.onLoading(
    execute: () -> Unit
): UiState<T> = apply {
    if (this is UiState.Loading) {
        println("TagUiState Loading")
        execute()
    }
}