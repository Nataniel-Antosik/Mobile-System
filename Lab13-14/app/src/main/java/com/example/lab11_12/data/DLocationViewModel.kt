package com.example.lab11_12.data

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class DLocationViewModel(application: Application):
    AndroidViewModel(application) {

    private var locationRepository: DLocationRepository =
        DLocationRepository(application)

    private var allLocation: Deferred<LiveData<List<DLocation>>> =
        locationRepository.getAllLocationAsync

    private var fiveElement: Deferred<LiveData<List<DLocation>>> =
        locationRepository.getLast5ElementAsync

    fun addLocation(location: DLocation){
        locationRepository.addlocation(location)
    }

    fun deleteLocation(){
        locationRepository.clear()
    }

    fun getAllLocation(): LiveData<List<DLocation>> = runBlocking {
        allLocation.await()
    }

    fun getFiveLocation(): LiveData<List<DLocation>> = runBlocking {
        fiveElement.await()
    }
}