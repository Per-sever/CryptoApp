package com.example.cryptoapp.domain

data class CoinInfoEntity(
    val fromSymbol: String,
    val toSymbol: String?,
    val price: String?,
    val minPrice: String?,
    val maxPrice: String?,
    val lastMarket: String?,
    val lastUpdate: String?,
    val imageUrl: String?
)