package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinAppRepository

class getCoinInfoListUseCase(private val repository: CoinAppRepository) {
    operator fun invoke() = repository.getCoinListInfo()
}