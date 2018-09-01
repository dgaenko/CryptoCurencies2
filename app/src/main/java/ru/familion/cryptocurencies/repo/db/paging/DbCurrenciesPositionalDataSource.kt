package ru.familion.cryptocurencies.repo.db.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PositionalDataSource
import android.util.Log
import com.github.ajalt.timberkt.Timber.d
import ru.familion.cryptocurencies.model.Data
import ru.familion.cryptocurencies.repo.db.CurrencyDao

class DbCurrenciesPositionalDataSource(val dao: CurrencyDao): PositionalDataSource<Data>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Data>) {
        d { "loadInitial, requestedStartPosition = " + params.requestedStartPosition + ", requestedLoadSize = " + params.requestedLoadSize }
        getPage(params.requestedStartPosition, params.requestedLoadSize) {
            repos, next -> callback.onResult(repos, next)
        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Data>) {
        d { "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize }
        getPage(params.startPosition, params.loadSize) {
            repos, next -> callback.onResult(repos)
        }
    }

    private fun getPage(startPosition: Int, limit: Int, callback: (repos: List<Data>, next: Int) -> Unit) {
        val currencies = dao.getPageList(startPosition, limit)
        callback(currencies, 0)
    }

}

