package com.example.learncompose

import android.app.Application
import com.example.learncompose.domain.appModule
import com.example.learncompose.domain.dataModule
import com.example.learncompose.domain.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                appModule,
                domainModule,
                dataModule
            )
        }
    }
}