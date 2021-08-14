package dev.weazyexe.core.ui

import java.io.Serializable

/**
 * Базовое состояние загрузки данных
 *
 * @property data загруженные данные
 * @property error [Exception], брошенная при загрузке
 * @property isLoading состояние загрузки
 * @property isSwipeRefresh состояние загрузки через свайп
 */
data class LoadState<T>(
    val data: T? = null,
    val error: Exception? = null,
    val isLoading: Boolean = false,
    val isSwipeRefresh: Boolean = false
) : Serializable {

    /**
     * Изменить текущее состояние на состояние загрузки
     */
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

    /**
     * Изменить текущее состояние на состояние ошибки
     */
    fun error(e: Exception): LoadState<T> =
        LoadState(
            data = null,
            error = e,
            isLoading = false,
            isSwipeRefresh = false
        )

    /**
     * Изменить текущее состояние на состояние успешной загрузки данных
     */
    fun data(data: T): LoadState<T> =
        LoadState(
            data = data,
            error = null,
            isLoading = false,
            isSwipeRefresh = false
        )
}