package com.example.lab11_12

import android.Manifest
import android.Manifest.permission.*
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lab11_12.data.Wifi
import com.example.lab11_12.data.WifiViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.*
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    private var txtWifiInfo: TextView? = null
    private var btnInfo: Button? = null
    private var lPermission: Button? = null

    private val FINE_LOCATION = 101
    private val WIFI_STATE = 102
    private val NETWORK_STATE = 103
    private val INTERNET2 = 104
    private val C_WIFI_STATE = 105

    var granted_LOCATION = 0

    private lateinit var mWifiViewModel: WifiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtWifiInfo = findViewById<TextView>(R.id.idTxt)
        btnInfo = findViewById<Button>(R.id.idBtn)
        lPermission = findViewById<Button>(R.id.locationPermission)


        mWifiViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WifiViewModel::class.java)



        buttonTaps()
        deleteDatabase()
    }

    fun calculateDistance(signalLevelInDb: Int, freqInMHz: Int): Double {
        val exp = (27.55 - 20 * kotlin.math.log10(freqInMHz.toDouble()) + kotlin.math.abs(
            signalLevelInDb
        )) / 20.0
        return pow(10.0, exp)
    }

    private fun buttonTaps(){
        lPermission?.setOnClickListener {
            val intent = Intent(applicationContext,
            ShowListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteDatabase(){
        delete_database?.setOnClickListener {
            mWifiViewModel.deleteWifi()
        }
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when {
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()

                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        fun innerCheck(name:String){
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT).show()
                granted_LOCATION = 0
            } else {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
                granted_LOCATION = 1
            }
        }
        when (requestCode) {
            FINE_LOCATION -> innerCheck("location")
            WIFI_STATE -> innerCheck("access wifi state")
            NETWORK_STATE -> innerCheck("network state")
            INTERNET2 -> innerCheck("internet")
            C_WIFI_STATE -> innerCheck("change wifi state")
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permision to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK"){ dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)

            }
        }
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }

    fun getWifiInformation(view: View?){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                Array<String>(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                1
            );
        }
        val wmgr = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wmgr.connectionInfo
        val ip = wifiInfo.ipAddress
        val macAddress = wifiInfo.macAddress
        val bssid = wifiInfo.bssid
        val rssi = wifiInfo.rssi
        val linkspeed = wifiInfo.linkSpeed
        val ssid = wifiInfo.ssid
        val networkId = wifiInfo.networkId
        val ipAddress = Formatter.formatIpAddress(ip)
        val frequency = wifiInfo.frequency
        val distance = calculateDistance(rssi, frequency)
        if(ContextCompat.checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            val info = "SSID: " + ssid +
                    "\n" + "RSSI: " + rssi + " dB" +
                    "\n" + "Link Speed: " + linkspeed +
                    "\n" + "Frequency: " + frequency + " MHz" +
                    "\n" + "Distance: " + distance.toInt() + " m"
            txtWifiInfo?.text = info
            insertDataToDatabase(ssid, rssi, linkspeed, frequency, distance)
        } else {
            val info = "SSID: " + "No permission" +
                    "\n" + "RSSI: " + rssi + " dB" +
                    "\n" + "Link Speed: " + linkspeed +
                    "\n" + "Frequency: " + frequency + " MHz" +
                    "\n" + "Distance: " + distance.toInt() + " m"
            txtWifiInfo?.text = info
        }

        /*val info = "Ipaddress: " + ipAddress +
                "\n" + "MacAddress: " + macAddress +
                "\n" + "BSSID: " + bssid +
                "\n" + "SSID: " + ssid +
                "\n" + "NetworkId: " + networkId +
                "\n" + "RSSI: " + rssi +
                "\n" + "Link Speed: " + linkspeed +
                "\n" + "Frequency: " + frequency +
                "\n" + "Distance: " + distance*/


    }

    private fun insertDataToDatabase(SSID: String, RSSI: Int, linkspeed: Int, frequency: Int, distance: Double) {
        //Create Wifi Object
        val wifi = Wifi(0,SSID, RSSI, linkspeed, frequency, distance)
        //Add Data to Database
        mWifiViewModel.addWifi(wifi)

        Toast.makeText(this,"Successfully added!", Toast.LENGTH_SHORT).show()
    }

}