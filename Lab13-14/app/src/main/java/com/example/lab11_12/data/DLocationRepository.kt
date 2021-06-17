package com.example.lab11_12.data

import android.app.Application
import android.location.Location
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import com.example.lab11_12.data.DLocation

class DLocationRepository(application: Application) {

    private var locationDao: DLocationDao

    init {
        val database = DLocationDatabase
            .getDatabase(application.applicationContext)

        locationDao = database!!.locationDao()
    }
    fun addlocation(location: DLocation) =
        CoroutineScope(Dispatchers.IO).launch{
            locationDao.insert(location)
        }

    fun clear() =
        CoroutineScope(Dispatchers.IO).launch{
            locationDao.clear()
        }

    val getAllLocationAsync: Deferred<LiveData<List<DLocation>>> =
    CoroutineScope(Dispatchers.IO).async{
        locationDao.getAllLocationInfo()
        }

    val getLast5ElementAsync: Deferred<LiveData<List<DLocation>>> =
        CoroutineScope(Dispatchers.IO).async{
            locationDao.getLast5Element()
        }
}