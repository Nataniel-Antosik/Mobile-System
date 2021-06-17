package com.example.lab11_12

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab11_12.data.Wifi
import com.example.lab11_12.data.WifiViewModel

class ShowListActivity: AppCompatActivity() {
    private lateinit var mWifiViewModel: WifiViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var listOfWifi: LiveData<List<Wifi>>
    
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        mWifiViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WifiViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        listOfWifi = mWifiViewModel.getAllWifi()
        listOfWifi.observe(this, Observer {
            if(it.isNotEmpty()){
                adapter = ListAdapter(it)
                recyclerView.adapter = adapter
            }
        })
    }
}