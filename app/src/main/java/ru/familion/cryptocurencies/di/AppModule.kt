package ru.familion.cryptocurencies.di

import android.app.Activity
import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.familion.cryptocurencies.R
import ru.familion.cryptocurencies.api.CoinmarketcapApi
import ru.familion.cryptocurencies.repo.CurrenciesRepository
import ru.familion.cryptocurencies.repo.db.CurrencyDao
import ru.familion.cryptocurencies.repo.db.CurrencyDatabase
import ru.familion.cryptocurencies.repo.net.Network
import ru.familion.cryptocurencies.ui.MainActivity
import ru.familion.cryptocurencies.util.AppConstants
import ru.familion.cryptocurencies.util.AppExecutors
import ru.familion.cryptocurencies.util.Utility
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule() {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): CurrencyDatabase = Room.databaseBuilder(context, CurrencyDatabase::class.java, AppConstants.APP_DB_NAME).build()

    @Provides
    @Singleton
    internal fun provideCurrencyDao(database: CurrencyDatabase): CurrencyDao {
        return database.currencyDao()
    }

    @Provides
    @Singleton
    fun provideExecutor(): AppExecutors {
        return AppExecutors.instance
    }

    @Provides
    @Singleton
    fun provideCurrenciesRepository(api: CoinmarketcapApi, currencyDatabase: CurrencyDatabase): CurrenciesRepository {
        return CurrenciesRepository(api, currencyDatabase)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Network.create(AppConstants.API_BASE_URL)
    }

    @Provides
    @Singleton
    fun provideCoinmarketcapApi(api: Retrofit): CoinmarketcapApi {
        return api.create(CoinmarketcapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUtility(context: Context) = Utility(context)

}
