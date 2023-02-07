package com.example.cryptoapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker @Inject constructor(
    context: Context,
    workerParameters: WorkerParameters,
    private val mapper: CoinMapper,
    private val coinListDao: CoinInfoDao,
    private val apiService: ApiService
) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoinsInfo = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
                val topCoinsNamesString = mapper.mapCoinListOfDataDTOToString(topCoinsInfo)
                val coinInfoList =
                    ApiFactory.apiService.getFullPriceList(fSyms = topCoinsNamesString)
                val coinInfoDTOList = mapper.mapJsonContainerToListCoinInfo(coinInfoList)
                val coinInfoDbModelList = mapper.mapListDtoModelToListDbModel(coinInfoDTOList)
                coinListDao.insertPriceList(coinInfoDbModelList)
                delay(10000)
            } catch (e: Exception) {
                delay(10000)
            }
        }
    }

    companion object {
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}