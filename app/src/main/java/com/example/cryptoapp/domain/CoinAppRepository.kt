package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData


interface CoinAppRepository {
    fun getCoinListInfo(): LiveData<List<CoinInfoEntity>>

    fun getCoinDetailInfo(fromSymbol: String): LiveData<CoinInfoEntity>

    suspend fun loadData()
}