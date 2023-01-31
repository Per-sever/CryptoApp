package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.repository.CoinAppRepositoryImpl
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.usecases.GetCoinDetailInfoUseCase
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinAppRepositoryImpl(application)

    private val getCoinDetailInfoUseCase = GetCoinDetailInfoUseCase(repository)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoListUseCase = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String): LiveData<CoinInfoEntity> {
        return getCoinDetailInfoUseCase(fSym)
    }


    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }


}