package dev.weazyexe.kudago.repository.events

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class EventsModule {

    @Provides
    fun provideEventsApi(retrofit: Retrofit): EventsApi =
        retrofit.create(EventsApi::class.java)
}