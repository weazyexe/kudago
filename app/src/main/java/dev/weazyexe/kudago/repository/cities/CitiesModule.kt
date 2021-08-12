package dev.weazyexe.kudago.repository.cities

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CitiesModule {

    @Provides
    fun provideCitiesApi(retrofit: Retrofit): CitiesApi =
        retrofit.create(CitiesApi::class.java)
}