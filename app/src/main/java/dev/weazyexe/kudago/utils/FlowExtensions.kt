package dev.weazyexe.kudago.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.handleErrors(handleBlock: suspend (Exception) -> Unit): Flow<T> =
    flow {
        try {
            collect { emit(it) }
        } catch (e: Exception) {
            handleBlock(e)
        }
    }