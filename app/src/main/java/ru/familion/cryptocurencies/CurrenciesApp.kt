package ru.familion.cryptocurencies

import android.app.Activity
import android.app.Application
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import com.github.ajalt.timberkt.Timber

import ru.familion.cryptocurencies.di.AppInjector


class CurrenciesApp: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

}