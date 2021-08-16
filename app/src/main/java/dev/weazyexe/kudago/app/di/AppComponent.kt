package dev.weazyexe.kudago.app.di

import dagger.Component
import dev.weazyexe.core.di.PerApplication
import dev.weazyexe.kudago.repository.cities.CitiesModule
import dev.weazyexe.kudago.repository.events.EventsModule
import dev.weazyexe.kudago.ui.screen.cities.CitiesViewModel
import dev.weazyexe.kudago.ui.screen.main.MainViewModel

@PerApplication
@Component(modules = [AppModule::class, EventsModule::class, CitiesModule::class])
interface AppComponent {

    fun inject(mainViewModel: MainViewModel)
    fun inject(citiesViewModel: CitiesViewModel)
}