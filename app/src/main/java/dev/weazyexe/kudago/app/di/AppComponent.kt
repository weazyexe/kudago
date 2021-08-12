package dev.weazyexe.kudago.app.di

import dagger.Component
import dev.weazyexe.kudago.repository.events.EventsModule
import dev.weazyexe.kudago.ui.screen.main.MainViewModel

@Component(modules = [AppModule::class, EventsModule::class])
interface AppComponent {

    fun inject(mainViewModel: MainViewModel)
}