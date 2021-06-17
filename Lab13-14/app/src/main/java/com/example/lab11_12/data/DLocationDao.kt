package com.example.lab11_12.data
import android.location.Location
import androidx.room.*
import androidx.lifecycle.LiveData

@Dao
interface DLocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(night: DLocation)

    @Update
    fun update(night: DLocation)

    @Query("SELECT * from Location_info WHERE id = :key")
    fun get(key: Long): DLocation?

    @Query("DELETE FROM Location_info")
    fun clear()

    @Query("SELECT * FROM Location_info ORDER BY id DESC LIMIT 1")
    fun getTonight(): DLocation?

    @Query("SELECT * FROM Location_info ORDER BY id ASC")
    fun getAllLocationInfo(): LiveData<List<DLocation>>

    @Query("SELECT * FROM Location_info ORDER BY id DESC LIMIT 5")
    fun getLast5Element(): LiveData<List<DLocation>>
}