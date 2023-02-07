package com.example.cryptoapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.usecases.GetCoinDetailInfoUseCase
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinDetailInfoUseCase: GetCoinDetailInfoUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {


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