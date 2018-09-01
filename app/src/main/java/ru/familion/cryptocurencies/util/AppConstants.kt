package ru.familion.cryptocurencies.util

import android.os.Environment


object AppConstants {

    val API_PAGE_SIZE = 10
    val API_BASE_URL = "https://api.coinmarketcap.com/v2/ticker/"
    //val APP_DB_NAME = Environment.getExternalStorageDirectory().getAbsolutePath() + "/currencies.db"
    val APP_DB_NAME = "currencies.db"

}