package ru.familion.cryptocurencies.repo

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

import ru.familion.cryptocurencies.model.Data
import ru.familion.cryptocurencies.util.AppConstants


abstract class RepoAbstract {

    lateinit var currenciesPaged: LiveData<PagedList<Data>>

    fun getCurrencies() = currenciesPaged

    fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(AppConstants.API_PAGE_SIZE)
            .setPageSize(AppConstants.API_PAGE_SIZE)
            .build()
    }
}