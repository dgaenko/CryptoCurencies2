package ru.familion.cryptocurencies.repo.db

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import ru.familion.cryptocurencies.model.Data
import ru.familion.cryptocurencies.repo.db.paging.DbCurrenciesDataSourceFactory
import ru.familion.cryptocurencies.util.AppConstants
import ru.familion.cryptocurencies.util.AppExecutors


class RepoDatabase(val currencyDatabase: CurrencyDatabase) {

    var currenciesPaged: LiveData<PagedList<Data>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(AppConstants.API_PAGE_SIZE)
            .setPageSize(AppConstants.API_PAGE_SIZE)
            .build()
        val executor = AppExecutors.instance.networkIO()
        val dataSourceFactory = DbCurrenciesDataSourceFactory(currencyDatabase.currencyDao())
        val livePagedListBuilder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
        currenciesPaged = livePagedListBuilder.
            setFetchExecutor(executor)
            .build()
    }

    fun getCurrencies() = currenciesPaged

}