package ru.familion.cryptocurencies.model

import com.google.gson.annotations.SerializedName

data class ApiResponse (

    @SerializedName("data")
    val data : List<Data>

)