package ru.familion.cryptocurencies.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.familion.cryptocurencies.model.ApiResponse

interface CoinmarketcapApi{

    @GET("?structure=array")
    fun getAll(): Call<ApiResponse>

    @GET("?structure=array")
    fun getPage(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): Call<ApiResponse>

}