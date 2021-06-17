package com.example.lab11_12.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Wifi_info")
class Wifi(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "SSID")
    val ssid: String = "",

    @ColumnInfo(name = "RSSI")
    var rssi: Int = -1,

    @ColumnInfo(name = "linkspeed")
    var linkspeed: Int = -1,

    @ColumnInfo(name = "frequency")
    var frequency: Int = -1,

    @ColumnInfo(name = "distance")
    var distance : Double = 0.0
    )
