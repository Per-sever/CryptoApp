package com.example.cryptoapp.data.models

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinJsonContainerDTO (
    @SerializedName("RAW")
    @Expose
    val coinInfoJsonObject: JsonObject? = null
)
