package com.example.lab11_12.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class WifiRepository(application: Application) {

    private var wifiDao: WifiDao

    init {
        val database = WifiDatabase
            .getDatabase(application.applicationContext)

        wifiDao = database!!.wifiDao()
    }
    fun addWifi(wifi: Wifi) =
        CoroutineScope(Dispatchers.IO).launch{
            wifiDao.insert(wifi)
        }

    fun clear() =
        CoroutineScope(Dispatchers.IO).launch{
            wifiDao.clear()
        }

    val getAllWifiAsync: Deferred<LiveData<List<Wifi>>> =
    CoroutineScope(Dispatchers.IO).async{
            wifiDao.getAllWifiInfo()
        }
}