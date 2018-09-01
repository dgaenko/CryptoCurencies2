package ru.familion.cryptocurencies.vm

import android.arch.lifecycle.ViewModel
import javax.inject.Inject

import ru.familion.cryptocurencies.repo.CurrenciesRepository


class CurrenciesListViewModel @Inject constructor(val currenciesRepository: CurrenciesRepository): ViewModel() {

    fun getCurrenciesList() = currenciesRepository.getCurrencies()
    fun getNetworkState() = currenciesRepository.getNetworkState()

}