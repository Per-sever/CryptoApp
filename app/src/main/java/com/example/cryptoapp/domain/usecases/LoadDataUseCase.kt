package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinAppRepository

class LoadDataUseCase(private val repository: CoinAppRepository) {
    operator fun invoke() = repository.loadData()
}