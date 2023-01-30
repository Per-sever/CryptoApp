package com.example.cryptoapp.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoListOfDataDTO (
    @SerializedName("Data")
    @Expose
    val coinNameList: List<CoinNameContainerDTO>? = null
)
