package com.example.lab11_12.data

import android.content.Context
import android.location.Location
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DLocation::class), version = 1, exportSchema = false)
public abstract class DLocationDatabase : RoomDatabase() {

    abstract fun locationDao(): DLocationDao

    companion object {

        @Volatile
        private var INSTANCE: DLocationDatabase? = null

        fun getDatabase(context: Context): DLocationDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DLocationDatabase::class.java,
                        "location_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}