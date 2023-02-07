package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.repository.CoinAppRepositoryImpl
import com.example.cryptoapp.domain.CoinAppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DataModule {
    @Binds
    abstract fun bindCoinAppRepositoryImpl(impl: CoinAppRepositoryImpl): CoinAppRepository

    companion object {
        @Provides
        fun provideCoinInfoDao(application: Application): CoinInfoDao {
            return AppDatabase.getInstance(application).coinInfoDao()
        }
    }
}