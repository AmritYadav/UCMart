package com.ucmart

import android.app.Application
import com.google.firebase.FirebaseApp
import com.ucmart.di.networkModule
import com.ucmart.di.repoModules
import com.ucmart.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class UcMartApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        val appModules: MutableList<Module> = ArrayList()
        appModules.add(networkModule)
        appModules.add(viewModels)
        appModules.addAll(repoModules)
        startKoin {
            androidContext(this@UcMartApp)
            modules(appModules)
        }
    }
}