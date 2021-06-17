package com.example.helloworldkotlin

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "onCreate")
    }

    override fun onStart(){
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume(){
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onStop(){
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onRestart(){
        super.onRestart()
        Log.i(TAG, "onRestart")
    }
}