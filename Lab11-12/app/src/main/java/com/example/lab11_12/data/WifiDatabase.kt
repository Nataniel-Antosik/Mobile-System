package com.example.lab11_12.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Wifi::class), version = 1, exportSchema = false)
public abstract class WifiDatabase : RoomDatabase() {

    abstract fun wifiDao(): WifiDao

    companion object {

        @Volatile
        private var INSTANCE: WifiDatabase? = null

        fun getDatabase(context: Context): WifiDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WifiDatabase::class.java,
                        "wifi_database"
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