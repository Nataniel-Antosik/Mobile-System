package com.example.lab11_12.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Location_info")
class DLocation(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "Latitude")
    val latitude: Double = 0.0,

    @ColumnInfo(name = "Longitude")
    val longitude: Double = 0.0,

    @ColumnInfo(name = "Altitude")
    val altitude: Double = 0.0,

    @ColumnInfo(name = "Date")
    var date: Long = 0L,

    @ColumnInfo(name = "Speed")
    var speed: Double = 0.0,

    @ColumnInfo(name = "Accuracy")
    var accuracy: Double = 0.0
    )
