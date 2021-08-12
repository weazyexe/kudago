package dev.weazyexe.kudago.app

import android.app.Application
import dev.weazyexe.kudago.app.di.AppComponent
import dev.weazyexe.kudago.app.di.AppModule
import dev.weazyexe.kudago.app.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }
}