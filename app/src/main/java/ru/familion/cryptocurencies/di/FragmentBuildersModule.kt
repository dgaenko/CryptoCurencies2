package ru.familion.cryptocurencies.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.familion.cryptocurencies.ui.currency.CurrencyFragment
import ru.familion.cryptocurencies.ui.list.CryptoCurrenciesListFragment


@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCurrenciesListFragment(): CryptoCurrenciesListFragment

    @ContributesAndroidInjector
    abstract fun contributeCurrencyFragment(): CurrencyFragment

}
