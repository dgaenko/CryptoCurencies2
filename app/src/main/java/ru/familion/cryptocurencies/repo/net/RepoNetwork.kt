package ru.familion.cryptocurencies.repo.net

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import ru.familion.cryptocurencies.model.Data
import ru.familion.cryptocurencies.repo.net.paging.NetCurrenciesDataSourceFactory
import ru.familion.cryptocurencies.repo.net.paging.NetPositionalDataSource
import ru.familion.cryptocurencies.util.AppConstants
import ru.familion.cryptocurencies.util.AppExecutors

class RepoNetwork(val dataSourceFactory: NetCurrenciesDataSourceFactory, boundaryCallback: PagedList.BoundaryCallback<Data>) {

    var currenciesPaged: LiveData<PagedList<Data>>
    var networkOperationState: LiveData<NetworkState>

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(AppConstants.API_PAGE_SIZE)
            .setPageSize(AppConstants.API_PAGE_SIZE)
            .build()
        networkOperationState = Transformations.switchMap(dataSourceFactory.getNetworkOperationStatus()) { NetPositionalDataSource.getNetworOperationkState() }
        val executor = AppExecutors.instance.networkIO()
        val livePagedListBuilder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
        currenciesPaged = livePagedListBuilder
            .setFetchExecutor(executor)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    fun getPagedCurrencies() = currenciesPaged

    fun getNetworkState() = networkOperationState

}