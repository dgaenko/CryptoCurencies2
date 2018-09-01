package ru.familion.cryptocurencies.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import ru.familion.cryptocurencies.api.CoinmarketcapApi
import javax.inject.Inject

import ru.familion.cryptocurencies.model.Data
import android.util.Log
import ru.familion.cryptocurencies.repo.db.CurrencyDatabase
import ru.familion.cryptocurencies.repo.db.RepoDatabase
import ru.familion.cryptocurencies.repo.net.NetworkState
import ru.familion.cryptocurencies.repo.net.RepoNetwork
import ru.familion.cryptocurencies.repo.net.paging.NetCurrenciesDataSourceFactory
import rx.schedulers.Schedulers


class CurrenciesRepository @Inject constructor(
        val api: CoinmarketcapApi,
        val currencyDatabase: CurrencyDatabase
) {

    var repoNetwork: RepoNetwork
    lateinit var repoDatabase: RepoDatabase
    lateinit var liveDataMerger: MediatorLiveData<PagedList<Data>>

    val boundaryCallback = object : PagedList.BoundaryCallback<Data>() {
        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            liveDataMerger.addSource(repoDatabase.getCurrencies()) { value ->
                liveDataMerger.setValue(value)
                liveDataMerger.removeSource(repoDatabase.getCurrencies())
            }
        }
    }

    init {
        val dataSourceFactory = NetCurrenciesDataSourceFactory(api)

        repoNetwork = RepoNetwork(dataSourceFactory, this.boundaryCallback)
        repoDatabase = RepoDatabase(currencyDatabase)
        liveDataMerger = MediatorLiveData()

        liveDataMerger.addSource(repoNetwork.getPagedCurrencies()) { value ->
            liveDataMerger.setValue(value)
        }
        dataSourceFactory.getCurrencies()
            .observeOn(Schedulers.io())
            .subscribe {
                data -> currencyDatabase.currencyDao().insert(data)
            }

    }

    fun getCurrencies() = liveDataMerger

    fun getNetworkState() = repoNetwork.getNetworkState()

}