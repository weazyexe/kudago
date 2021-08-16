package dev.weazyexe.kudago.repository.cities

import dev.weazyexe.core.di.PerApplication
import dev.weazyexe.core.network.transform
import dev.weazyexe.kudago.domain.city.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Репозиторий с данными о городах
 */
@PerApplication
class CitiesRepository @Inject constructor(
    private val citiesStorage: CitiesStorage,
    private val citiesApi: CitiesApi
) {

    /**
     * Получение текущего выбранного города
     */
    fun getCurrentCity(): Flow<String> =
        citiesStorage.getCurrentCity()

    /**
     * Обновление текущего выбранного города
     */
    fun updateCurrentCity(city: String): Flow<Boolean> =
        citiesStorage.updateCurrentCity(city)

    /**
     * Получение списка всех городов
     */
    fun getCities(): Flow<List<City>> = flow {
        emit(citiesApi.getCities().transform())
    }

    /**
     * Получение города по его [slug]
     */
    fun getCityBySlug(slug: String): Flow<City> = flow {
        emit(citiesApi.getCityBySlug(slug).transform())
    }
}