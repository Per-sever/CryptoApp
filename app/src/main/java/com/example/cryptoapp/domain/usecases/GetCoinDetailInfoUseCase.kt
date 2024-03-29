package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinAppRepository

class GetCoinDetailInfoUseCase(private val repository: CoinAppRepository) {
    operator fun invoke(fromSymbol: String) = repository.getCoinDetailInfo(fromSymbol)
}