package ru.familion.cryptocurencies.repo.net

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.github.ajalt.timberkt.Timber.d

import ru.familion.cryptocurencies.api.UserAgentInterceptor

class Network {

    companion object {

        const val BASE_URL = "https://api.coinmarketcap.com/v2/ticker/"
        var userAgent: String = System.getProperty("http.agent")

        fun create(url: String = BASE_URL, userAgent: String? = null): Retrofit {
            val httpUrl = HttpUrl.parse(url)
            var UA = userAgent ?: this.userAgent

            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { d { it } } )
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(UserAgentInterceptor(UA))
                .build()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

}