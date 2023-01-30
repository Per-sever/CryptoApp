package com.example.cryptoapp.domain


interface CoinAppRepository {
    fun getCoinInfoList(fromSymbol: String): List<CoinInfoEntity>

    fun getCoinDetailInfo(): CoinInfoEntity

    fun loadData()
}