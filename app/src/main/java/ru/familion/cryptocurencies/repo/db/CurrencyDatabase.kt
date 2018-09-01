package ru.familion.cryptocurencies.repo.db

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database

import ru.familion.cryptocurencies.model.Data


@Database(
        entities = [Data::class],
        version = 1,
        exportSchema = false
)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

}