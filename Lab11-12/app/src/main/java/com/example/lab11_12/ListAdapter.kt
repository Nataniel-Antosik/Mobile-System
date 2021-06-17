package com.example.lab11_12

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab11_12.data.Wifi
import kotlinx.android.synthetic.main.custom_row.view.*


class ListAdapter(private val wifiList: List<Wifi>): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


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
        val currentItem = wifiList[position]
        holder.idTextView.text = currentItem.id.toString()
        val info = "SSID: " + currentItem.ssid +
                "\n" + "RSSI: " + currentItem.rssi.toString() + " dB" +
                "\n" + "Link Speed: " + currentItem.linkspeed.toString() +
                "\n" + "Frequency: " + currentItem.frequency.toString() + " MHz" +
                "\n" + "Distance: " + currentItem.distance.toInt() + " m"
        holder.infoTextView.text = info
    }

    override fun getItemCount(): Int {
        return wifiList.size
    }

}