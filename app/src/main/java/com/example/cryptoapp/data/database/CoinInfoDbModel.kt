package com.example.cryptoapp.data.database

data class CoinInfoDbModel (
    val fromSymbol: String,
    val toSymbol: String?,
    val price: String?,
    val minPrice: String?,
    val maxPrice: String?,
    val lastMarket: String?,
    val lastUpdate: Long?
)