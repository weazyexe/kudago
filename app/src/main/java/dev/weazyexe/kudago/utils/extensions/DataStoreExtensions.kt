package dev.weazyexe.kudago.utils.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Взять из [DataStore] значение типа [T]
 */
fun <T> DataStore<Preferences>.get(
    key: Preferences.Key<T>,
    default: T
): Flow<T> = data.map { prefs ->
    prefs[key] ?: default
}

/**
 * Записать в [DataStore] значение типа [T]
 */
suspend fun <T> DataStore<Preferences>.put(
    key: Preferences.Key<T>,
    value: T
) {
    edit { prefs ->
        prefs[key] = value
    }
}