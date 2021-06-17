package com.example.coding_challenge_activity_lifecycle_and_state

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var shop_list1 = ArrayList<String>()
    private var list: ListView? = null
    private var adapter: ArrayAdapter<String>? = null
    private var mLocationEditText: EditText? = null
    var TEXT_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = findViewById<View>(R.id.listView1) as ListView
        mLocationEditText = findViewById(R.id.location_edittext)

        val empty_list = listOf("Shopping list")

        for (i in empty_list){
            shop_list1.add(i)
        }
        adapter = ArrayAdapter(this, R.layout.item, shop_list1)
        list!!.setAdapter(adapter)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("myArrayList", shop_list1)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        shop_list1 = savedInstanceState.getStringArrayList("myArrayList") as ArrayList<String>
    }

    fun startSecondActivity(view: View){
        val intent = Intent(this, SecondActivity::class.java)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == TEXT_REQUEST) {
        if (resultCode == RESULT_OK) {
            val tmp = data?.getStringExtra(SecondActivity().EXTRA_REPLY)
            if (tmp != null) {
                    if(shop_list1.size < 11){
                        shop_list1.add(tmp)
                    } else {
                        val toast = Toast.makeText(applicationContext, "Jest 10 produktow", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
            }
            adapter = ArrayAdapter(this, R.layout.item, shop_list1)
            list!!.setAdapter(adapter)
        }
    }

    /* Coding challange Android fundamentals 02.3: Implicit intents */
    fun openLocation(view: View?) {
        // Get the string indicating a location. Input is not validated; it is
        // passed to the location handler intact.
        val loc = mLocationEditText!!.text.toString()

        // Parse the location and create the intent.
        val addressUri = Uri.parse("geo:0,0?q=$loc")
        val intent = Intent(Intent.ACTION_VIEW, addressUri)

        // Find an activity to handle the intent, and start that activity.
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!")
        }
    }
}
