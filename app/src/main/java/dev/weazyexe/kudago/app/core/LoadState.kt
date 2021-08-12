package dev.weazyexe.kudago.app.core

data class LoadState<T>(
    val data: T? = null,
    val error: Exception? = null,
    val isLoading: Boolean = false,
    val isSwipeRefresh: Boolean = false
) {

    fun loading(isSwipeRefresh: Boolean = false): LoadState<T> =
        LoadState(
            data = if (isSwipeRefresh) {
                data
            } else {
                null
            },
            error = null,
            isLoading = true,
            isSwipeRefresh = isSwipeRefresh
        )

    fun error(e: Exception): LoadState<T> =
        LoadState(
            data = null,
            error = e,
            isLoading = false,
            isSwipeRefresh = false
        )

    fun data(data: T): LoadState<T> =
        LoadState(
            data = data,
            error = null,
            isLoading = false,
            isSwipeRefresh = false
        )
}