package com.example.lab11_12

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lab11_12.Constans.CHANNEL_ID
import com.example.lab11_12.Constans.MY_NOTIFICATION_ID
import com.example.lab11_12.data.DLocation
import com.example.lab11_12.data.DLocationViewModel
import java.text.SimpleDateFormat
import java.util.*

class MyService: Service(), LocationListener {

    lateinit var locationManager: LocationManager
    private lateinit var mLocationViewModel: DLocationViewModel

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate(){
        super.onCreate()

        mLocationViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(DLocationViewModel::class.java)

        initSomething()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int{

        showNotification()


        return START_STICKY
    }

    private fun showNotification(){
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this,0, notificationIntent, 0)

        val notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentText("I work in the background")
            .setSmallIcon(R.drawable.ic_baseline_location_on_24)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(MY_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviseChannel = NotificationChannel(
                CHANNEL_ID, "My Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviseChannel)
        }
    }

    override fun onLocationChanged(location: Location) {
        val time = location.time
        val date = Date(time)
        val sdf = SimpleDateFormat(" HH:mm:ss yyyy-MM-dd")
        val info = "Szerokość: " + location.latitude +
                "\n" + "Długość : " + location.longitude +
                "\n" + "Wysokość: " + location.altitude +
                "\n" + "Czas : " + sdf.format(date) +
                "\n" + "Prędkość : " +  location.speed.toDouble().toString()  +
                "\n" + "Dokładność : " + location.accuracy
        insertDataToDatabase(location.latitude, location.longitude, location.altitude, location.time, location.speed.toDouble(), location.accuracy.toDouble())

    }

    private fun initSomething(){
        if(ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, this)
        }
    }

    private fun insertDataToDatabase(latitude: Double, longitude: Double, altitude: Double, date: Long, speed: Double, accuracy: Double) {
        //Create Location Object
        val location = DLocation(0,latitude, longitude, altitude, date, speed, accuracy)
        //Add Data to Database
        mLocationViewModel.addLocation(location)

        //Toast.makeText(this,"Successfully added!", Toast.LENGTH_SHORT).show()
    }
}
