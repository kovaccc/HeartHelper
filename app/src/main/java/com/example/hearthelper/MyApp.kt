package com.example.hearthelper

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import android.app.Application
import com.example.hearthelper.module.viewModelModule

class MyApp : Application() {

    override fun onCreate()
    {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp) // access to your application
            modules(listOf(viewModelModule)) //you can add list of modules here
        }
    }

}