package ru.familion.cryptocurencies

import android.util.Log
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.familion.cryptocurencies.api.CoinmarketcapApi
import ru.familion.cryptocurencies.model.ApiResponse

class ApiUnitTest {
    @Test
    fun apiRequestIsCorrect() {

        Assert.assertEquals(4, 2 + 2)
    }

}