package ru.familion.cryptocurencies.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

import ru.familion.cryptocurencies.vm.CurrenciesListViewModel
import ru.familion.cryptocurencies.vm.CurrencyDetailsViewModel
import ru.familion.cryptocurencies.vm.factory.ViewModelFactory
import ru.familion.cryptocurencies.vm.factory.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrenciesListViewModel::class)
    internal abstract fun bindCurrenciesListViewModel(currenciesListViewModel: CurrenciesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyDetailsViewModel::class)
    internal abstract fun bindCurrencyDetailsViewModel(currencyDetailsViewModel: CurrencyDetailsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
