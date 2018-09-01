package ru.familion.cryptocurencies.repo.net.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import ru.familion.cryptocurencies.api.CoinmarketcapApi

import ru.familion.cryptocurencies.model.Data
import rx.subjects.ReplaySubject


class NetCurrenciesDataSourceFactory(val api: CoinmarketcapApi): DataSource.Factory<Int, Data>() {

    val networkStatus: MutableLiveData<NetPositionalDataSource>
    val currenciesPageKeyedDataSource: NetPositionalDataSource


    fun getCurrencies(): ReplaySubject<Data> {
        return currenciesPageKeyedDataSource.geCurrencies()
    }

    init {
        networkStatus = MutableLiveData<NetPositionalDataSource>()
        currenciesPageKeyedDataSource = NetPositionalDataSource(api)
    }

    fun getNetworkOperationStatus() = networkStatus

    override fun create(): NetPositionalDataSource {
        networkStatus.postValue(currenciesPageKeyedDataSource)
        return currenciesPageKeyedDataSource
    }

}
