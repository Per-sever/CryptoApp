package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinAppRepository
import javax.inject.Inject

class GetCoinDetailInfoUseCase @Inject constructor(private val repository: CoinAppRepository) {
    operator fun invoke(fromSymbol: String) = repository.getCoinDetailInfo(fromSymbol)
}