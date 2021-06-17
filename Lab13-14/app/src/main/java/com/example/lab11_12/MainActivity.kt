package com.example.lab11_12

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab11_12.data.DLocation
import com.example.lab11_12.data.DLocationViewModel
import com.example.lab11_12.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), LocationListener {
    private var txtLocationInfo: TextView? = null
    private var mean: TextView? = null

    private var btnInfo: Button? = null
    private var bDatabase: Button? = null

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var listOfLocation: LiveData<List<DLocation>>

    private lateinit var mLocationViewModel: DLocationViewModel

    lateinit var locationManager: LocationManager
    private var hasGPS = false
    private var hasNetwork = false
    private var indeks = 0
    var meanLatitude: MutableList<Double> = ArrayList()
    var meanLongitude: MutableList<Double> = ArrayList()
    var meanAltitude: MutableList<Double> = ArrayList()
    var meanSpeed: MutableList<Double> = ArrayList()
    var meanAccuracy: MutableList<Double> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        txtLocationInfo = findViewById<TextView>(R.id.idTxt)
        mean = findViewById<TextView>(R.id.meanValue)
        btnInfo = findViewById<Button>(R.id.idBtn)
        bDatabase = findViewById<Button>(R.id.buttonDatabase)

        mLocationViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(DLocationViewModel::class.java)

        buttonTaps()
        binding.startServiceBT.setOnClickListener {
            startStopService()
        }

        stopGetLocation.setOnClickListener {
            stopOnClick()
            txtLocationInfo?.text = "Location download stopped"
        }
        //deleteDatabase()
    }

    private fun startStopService(){
        if(isMyServiceRunning(MyService::class.java)){
            Toast.makeText(
                this,
                "Service Stopped",
                Toast.LENGTH_SHORT
            ).show()

            stopService(Intent(this, MyService::class.java))
        } else {
            Toast.makeText(
                this,
                "Service Started",
                Toast.LENGTH_SHORT
            ).show()

            startService(Intent(this, MyService::class.java))
        }
    }

    private fun isMyServiceRunning(mClass: Class<MyService>): Boolean{
        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for(service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)){
            if (mClass.name.equals(service.service.className)){
                return true
            }

        }
        return false
    }

    private fun buttonTaps(){
        bDatabase?.setOnClickListener {
            val intent = Intent(applicationContext,
            ShowListActivity::class.java)
            startActivity(intent)
        }
    }

    /*private fun deleteDatabase(){
        delete_database?.setOnClickListener {
            mWifiViewModel.deleteWifi()
        }
    }*/

    fun getFiveElement(view: View?){
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        listOfLocation = mLocationViewModel.getFiveLocation()
        listOfLocation.observe(this, Observer {
            if(it.isNotEmpty()){
                adapter = ListAdapter(it)
                recyclerView.adapter = adapter
            }
        })

    }

    fun getLocation(view: View?){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                Array<String>(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                1
            );
        }
        if(ContextCompat.checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, this)
        } else {
            txtLocationInfo?.text = "I have no permission"
        }
    }

    fun stopOnClick() {
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        val time = location.time
        val date = Date(time)
        val sdf = SimpleDateFormat(" HH:mm:ss yyyy-MM-dd")
        indeks += 1
        val info = "Szerokość: " + location.latitude +
                "\n" + "Długość : " + location.longitude +
                "\n" + "Wysokość: " + location.altitude +
                "\n" + "Czas : " + sdf.format(date) +
                "\n" + "Prędkość : " +  location.speed.toDouble().toString()  +
                "\n" + "Dokładność : " + location.accuracy
        txtLocationInfo?.text = info
        insertDataToDatabase(location.latitude, location.longitude, location.altitude, location.time, location.speed.toDouble(), location.accuracy.toDouble())

    }

    private fun insertDataToDatabase(latitude: Double, longitude: Double, altitude: Double, date: Long, speed: Double, accuracy: Double) {
        //Create Location Object
        val location = DLocation(0,latitude, longitude, altitude, date, speed, accuracy)
        //Add Data to Database
        mLocationViewModel.addLocation(location)

        //Toast.makeText(this,"Successfully added!", Toast.LENGTH_SHORT).show()
    }


}