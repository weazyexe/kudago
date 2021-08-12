package dev.weazyexe.kudago.repository.cities

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.weazyexe.kudago.utils.extensions.get
import dev.weazyexe.kudago.utils.extensions.put
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val CITIES_STORAGE_NAME = "CITIES_STORAGE_NAME"
private val CURRENT_CITY_KEY = stringPreferencesKey("CURRENT_CITY_KEY")
private const val DEFAULT_CITY = "msk"

/**
 * Локальное хранилище данных о городах
 */
class CitiesStorage @Inject constructor(
    private val context: Context
) {

    private val Context.dataStore by preferencesDataStore(CITIES_STORAGE_NAME)

    fun getCurrentCity(): Flow<String> =
        context.dataStore.get(CURRENT_CITY_KEY, DEFAULT_CITY)

    suspend fun updateCurrentCity(city: String) {
        context.dataStore.put(CURRENT_CITY_KEY, city)
    }
}