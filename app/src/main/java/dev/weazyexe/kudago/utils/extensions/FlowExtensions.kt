package dev.weazyexe.kudago.utils.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Обработка ошибок в [Flow]
 */
fun <T> Flow<T>.handleErrors(
    handleBlock: suspend (Exception) -> Unit
): Flow<T> = flow {
    try {
        collect { emit(it) }
    } catch (e: Exception) {
        handleBlock(e)
    }
}