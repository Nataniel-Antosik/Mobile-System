package com.example.lab11_12.data
import androidx.room.*
import androidx.lifecycle.LiveData

@Dao
interface WifiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(night: Wifi)

    @Update
    fun update(night: Wifi)

    @Query("SELECT * from Wifi_info WHERE id = :key")
    fun get(key: Long): Wifi?

    @Query("DELETE FROM Wifi_info")
    fun clear()

    @Query("SELECT * FROM Wifi_info ORDER BY id DESC LIMIT 1")
    fun getTonight(): Wifi?

    @Query("SELECT * FROM Wifi_info ORDER BY id DESC")
    fun getAllWifiInfo(): LiveData<List<Wifi>>

}