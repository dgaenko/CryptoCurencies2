package ru.familion.cryptocurencies.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName;

@Entity(
        tableName = "currencies_table"
)
data class Data (

        @PrimaryKey
        @SerializedName("id")
        var id : Int,

        @SerializedName("name")
        var name : String,

        @SerializedName("symbol")
        var symbol : String,

        @SerializedName("website_slug")
        var website_slug : String,

        @SerializedName("rank")
        var rank : Int,

        @SerializedName("circulating_supply")
        var circulating_supply : Double,

        @SerializedName("total_supply")
        var total_supply : Double,

        @SerializedName("max_supply")
        var max_supply : Double?,

        @SerializedName("quotes")
        @Embedded
        var quotes : Quotes,

        @SerializedName("last_updated")
        var last_updated : Int,

        @SerializedName("num")
        var num : Int

)
