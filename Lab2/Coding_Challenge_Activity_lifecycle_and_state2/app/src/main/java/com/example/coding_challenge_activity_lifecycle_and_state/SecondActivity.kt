package com.example.coding_challenge_activity_lifecycle_and_state

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    val EXTRA_REPLY = "com.example.twoactivities.extra.REPLY"
    val shop_list2 = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun toastInformation(info: String){
        val toast = Toast.makeText(applicationContext, info, Toast.LENGTH_LONG)
        toast.show()
    }

    fun setRice(view: View) {
        val reply = "Rice"
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    fun setCheese(view: View) {
        val reply = "Cheese"
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    fun setBread(view: View) {
        val reply = "Bread"
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    fun setFish(view: View){
        val reply = "Fish"
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    fun setApple(view: View){
        val reply = "Apple"
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    fun setEgg(view: View){
        val reply = "Egg"
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    fun setButter(view: View){
        val reply = "Butter"
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    fun setBanana(view: View){
        val reply = "Banana"
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

}