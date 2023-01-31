package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.data.workers.RefreshDataWorker
import com.example.cryptoapp.domain.CoinAppRepository
import com.example.cryptoapp.domain.CoinInfoEntity

class CoinAppRepositoryImpl(private val application: Application) : CoinAppRepository {

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

    override fun loadData() {
        val workerRefreshData = WorkManager.getInstance(application)
        workerRefreshData.enqueueUniqueWork(
            WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }

    companion object {
        private const val WORKER_NAME = "RefreshDataWorker"
    }
}