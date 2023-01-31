package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.CoinAppRepository
import com.example.cryptoapp.domain.CoinInfoEntity

class CoinAppRepositoryImpl(application: Application) : CoinAppRepository {

    private val coinListDao = AppDatabase.getInstance(application).coinPriceInfoDao()

    private val mapper = CoinMapper()

    override fun getCoinListInfo(): LiveData<List<CoinInfoEntity>> {
        return Transformations.map(coinListDao.getPriceList()) {
            mapper.mapListDbModelToListEntity(it)
        }
    }

    override fun getCoinDetailInfo(fromSymbol: String): LiveData<CoinInfoEntity> {
        return Transformations.map(coinListDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            val topCoinsInfo = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
            val topCoinsNamesString = mapper.mapCoinListOfDataDTOToString(topCoinsInfo)
            val coinInfoList = ApiFactory.apiService.getFullPriceList(fSyms = topCoinsNamesString)
            val coinInfoDTOList = mapper.mapJsonContainerToListCoinInfo(coinInfoList)
            val coinInfoDbModelList = mapper.mapListDtoModelToListDbModel(coinInfoDTOList)
            coinListDao.insertPriceList(coinInfoDbModelList)
        }
    }
}