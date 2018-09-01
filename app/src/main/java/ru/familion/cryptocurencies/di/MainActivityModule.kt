package ru.familion.cryptocurencies.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.familion.cryptocurencies.ui.MainActivity

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

}
