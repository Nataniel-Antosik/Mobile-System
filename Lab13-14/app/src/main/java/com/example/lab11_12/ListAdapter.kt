package com.example.lab11_12

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab11_12.data.DLocation
import kotlinx.android.synthetic.main.custom_row.view.*
import java.text.SimpleDateFormat
import java.util.*


class ListAdapter(private val locationList: List<DLocation>): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.id_txt)
        val infoTextView: TextView = itemView.findViewById(R.id.data_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.custom_row, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = locationList[position]
        val date = Date(currentItem.date)
        val sdf = SimpleDateFormat(" HH:mm:ss yyyy-MM-dd")
        holder.idTextView.text = currentItem.id.toString()
        val info = "Szerokość: " + currentItem.latitude +
                "\n" + "Długość : " + currentItem.longitude +
                "\n" + "Wysokość: " + currentItem.altitude +
                "\n" + "Czas : " + sdf.format(date) +
                "\n" + "Prędkość : " +  currentItem.speed.toDouble().toString()  +
                "\n" + "Dokładność : " +currentItem.accuracy
        holder.infoTextView.text = info
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

}