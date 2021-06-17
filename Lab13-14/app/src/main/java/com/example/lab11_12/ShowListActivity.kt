package com.example.lab11_12

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab11_12.data.DLocationViewModel
import com.example.lab11_12.data.DLocation

class ShowListActivity: AppCompatActivity() {
    private lateinit var mLocationViewModel: DLocationViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var listOfLocation: LiveData<List<DLocation>>
    
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        mLocationViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(DLocationViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        listOfLocation = mLocationViewModel.getAllLocation()
        listOfLocation.observe(this, Observer {
            if(it.isNotEmpty()){
                adapter = ListAdapter(it)
                recyclerView.adapter = adapter
            }
        })
    }
}