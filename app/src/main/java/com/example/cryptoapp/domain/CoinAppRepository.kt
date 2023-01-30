package com.example.cryptoapp.domain


interface CoinAppRepository {
    fun getCoinListInfo(): List<CoinInfoEntity>

    fun getCoinDetailInfo(fromSymbol: String): CoinInfoEntity

    fun loadData()
}