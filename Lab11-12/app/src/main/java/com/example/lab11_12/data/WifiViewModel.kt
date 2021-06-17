package com.example.lab11_12.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lab11_12.MainActivity
import kotlinx.coroutines.*

class WifiViewModel(application: Application):
    AndroidViewModel(application) {

    private var wifiRepository: WifiRepository =
        WifiRepository(application)
    private var allWifi: Deferred<LiveData<List<Wifi>>> =
        wifiRepository.getAllWifiAsync



    fun addWifi(wifi: Wifi){
        wifiRepository.addWifi(wifi)
    }

    fun deleteWifi(){
        wifiRepository.clear()
    }

    fun getAllWifi(): LiveData<List<Wifi>> = runBlocking {
        allWifi.await()
    }
}