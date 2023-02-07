package com.example.cryptoapp.data.mappers

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.models.CoinInfoDTO
import com.example.cryptoapp.data.network.models.CoinInfoListOfDataDTO
import com.example.cryptoapp.data.network.models.CoinJsonContainerDTO
import com.example.cryptoapp.domain.CoinInfoEntity
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor() {
    fun mapDbModelToEntity(coinInfoDbModel: CoinInfoDbModel): CoinInfoEntity {
        return CoinInfoEntity(
            fromSymbol = coinInfoDbModel.fromSymbol,
            toSymbol = coinInfoDbModel.toSymbol,
            price = coinInfoDbModel.price,
            minPrice = coinInfoDbModel.minPrice,
            maxPrice = coinInfoDbModel.maxPrice,
            lastMarket = coinInfoDbModel.lastMarket,
            lastUpdate = convertTimestampToTime(coinInfoDbModel.lastUpdate),
            imageUrl = coinInfoDbModel.imageUrl

        )
    }


    fun mapDTOModelToDbModel(coinInfoDTO: CoinInfoDTO): CoinInfoDbModel {
        return CoinInfoDbModel(
            fromSymbol = coinInfoDTO.fromSymbol,
            toSymbol = coinInfoDTO.toSymbol,
            price = coinInfoDTO.price,
            minPrice = coinInfoDTO.lowDay,
            maxPrice = coinInfoDTO.highDay,
            lastMarket = coinInfoDTO.lastMarket,
            lastUpdate = coinInfoDTO.lastUpdate,
            imageUrl = BASE_IMAGE_URL + coinInfoDTO.imageUrl
        )
    }


    fun mapListDbModelToListEntity(listDbModel: List<CoinInfoDbModel>): List<CoinInfoEntity> {
        return listDbModel.map { mapDbModelToEntity(it) }
    }

    fun mapCoinListOfDataDTOToString(coinListOfData: CoinInfoListOfDataDTO): String {
        return coinListOfData.coinNameList?.map {
            it.coinNameDTO?.name
        }?.joinToString(",") ?: EMPTY_SYMBOL
    }

    fun mapListDtoModelToListDbModel(coinInfoListDTO: List<CoinInfoDTO>): List<CoinInfoDbModel> {
        return coinInfoListDTO.map { mapDTOModelToDbModel(it) }
    }

    fun mapJsonContainerToListCoinInfo(coinJsonContainerDTO: CoinJsonContainerDTO): List<CoinInfoDTO> {
        val result = mutableListOf<CoinInfoDTO>()
        val jsonObject = coinJsonContainerDTO.coinInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDTO::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        private const val EMPTY_SYMBOL = ""
        private const val BASE_IMAGE_URL = "https://cryptocompare.com"

    }
}