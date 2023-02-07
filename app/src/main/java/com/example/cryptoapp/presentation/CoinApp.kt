package com.example.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.workers.RefreshDataWorkerFactory
import com.example.cryptoapp.di.ApplicationScope
import com.example.cryptoapp.di.DaggerApplicationComponent
import javax.inject.Inject

@ApplicationScope
class CoinApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: RefreshDataWorkerFactory
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(
            workerFactory
        ).build()
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}