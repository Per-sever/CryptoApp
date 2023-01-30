package com.example.cryptoapp.data.mappers

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.models.CoinInfoDTO
import com.example.cryptoapp.data.network.models.CoinInfoListOfDataDTO
import com.example.cryptoapp.data.network.models.CoinJsonContainerDTO
import com.example.cryptoapp.domain.CoinInfoEntity
import com.google.gson.Gson

class CoinMapper {
    fun mapDbModelToEntity(coinInfoDbModel: CoinInfoDbModel): CoinInfoEntity {
        return CoinInfoEntity(
            fromSymbol = coinInfoDbModel.fromSymbol,
            toSymbol = coinInfoDbModel.toSymbol,
            price = coinInfoDbModel.price,
            minPrice = coinInfoDbModel.minPrice,
            maxPrice = coinInfoDbModel.maxPrice,
            lastMarket = coinInfoDbModel.lastMarket,
            lastUpdate = coinInfoDbModel.lastUpdate
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
            lastUpdate = coinInfoDTO.lastUpdate
        )
    }


    fun mapListDbModelToListEntity(listDbModel: List<CoinInfoDbModel>): List<CoinInfoEntity> {
        return listDbModel.map { mapDbModelToEntity(it) }
    }

    fun mapCoinListOfDataDTOToString(coinListOfData: CoinInfoListOfDataDTO): String {
        return coinListOfData.coinNameList?.map {
            it.coinNameDTO?.name
        }?.joinToString(",") ?: ""
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
}