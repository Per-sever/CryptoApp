package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinAppRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: CoinAppRepository) {
    operator fun invoke() = repository.loadData()
}