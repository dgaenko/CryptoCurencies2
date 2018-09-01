package ru.familion.cryptocurencies.repo.db.paging

import android.arch.paging.DataSource
import ru.familion.cryptocurencies.repo.db.CurrencyDao
import ru.familion.cryptocurencies.model.Data

class DbCurrenciesDataSourceFactory(dao: CurrencyDao) : DataSource.Factory<Int, Data>() {

    private val currenciesPositionalDataSource = DbCurrenciesPositionalDataSource(dao)

    override fun create(): DataSource<Int, Data> {
        return currenciesPositionalDataSource
    }

}
