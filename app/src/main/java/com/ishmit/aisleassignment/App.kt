package com.ishmit.aisleassignment

import android.app.Application
import com.ishmit.aisleassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin dependency injection framework
        startKoin {
            // Provide the application context
            androidContext(this@App)
            // Load the appModule containing the dependency definitions
            modules(appModule)
        }
    }
}
