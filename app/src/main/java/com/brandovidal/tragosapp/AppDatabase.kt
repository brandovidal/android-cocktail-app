package com.brandovidal.tragosapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brandovidal.tragosapp.data.model.DrinkEntity
import com.brandovidal.tragosapp.domain.DrinkDao

@Database(entities = arrayOf(DrinkEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "table_drink"
            ).build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}