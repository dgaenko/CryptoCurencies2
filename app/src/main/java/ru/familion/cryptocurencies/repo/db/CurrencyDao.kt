package ru.familion.cryptocurencies.repo.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Update
import android.arch.persistence.room.OnConflictStrategy.REPLACE

import ru.familion.cryptocurencies.model.Data


@Dao
public interface CurrencyDao {

    @Query("SELECT * FROM currencies_table")
    fun getAllList(): List<Data>

    @Query("SELECT * FROM currencies_table WHERE num>:num LIMIT :limit")
    fun getPageList(num: Int, limit: Int): List<Data>

    @Insert(onConflict = REPLACE)
    fun insert(data: Data)

    @Update
    fun update(data: Data)

    @Delete
    fun delete(data: Data)

}