package ru.familion.cryptocurencies.model

import com.google.gson.annotations.SerializedName;

data class USD (

        @SerializedName("price")
        var price : Double,

        @SerializedName("volume_24h")
        var volume_24h : Double,

        @SerializedName("market_cap")
        var market_cap : Double,

        @SerializedName("percent_change_1h")
        var percent_change_1h : Double,

        @SerializedName("percent_change_24h")
        var percent_change_24h : Double,

        @SerializedName("percent_change_7d")
        var percent_change_7d : Double

)