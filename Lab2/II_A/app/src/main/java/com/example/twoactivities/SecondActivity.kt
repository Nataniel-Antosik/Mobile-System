package com.example.twoactivities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SecondActivity : AppCompatActivity() {
    private val LOG_TAG = SecondActivity::class.java.simpleName
    val EXTRA_REPLY = "com.example.twoactivities.extra.REPLY"
    private var mReply: EditText? = null
    private var scrollText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val intent = intent
        val message = intent.getStringExtra(MainActivity().EXTRA_MESSAGE)
        val message_one = intent.getStringExtra(MainActivity().EXTRA_MESSAGE_ONE)
        val message_two = intent.getStringExtra(MainActivity().EXTRA_MESSAGE_TWO)
        val message_three = intent.getStringExtra(MainActivity().EXTRA_MESSAGE_THREE)
        val textView = findViewById<TextView>(R.id.text_message)
        scrollText = findViewById<TextView>(R.id.scroll_text)
        mReply = findViewById<EditText>(R.id.editText_second)

        if(message != null){
            textView.text = message
        } else if (message_one != null) {
            scrollText?.text = message_one
        } else if (message_two != null) {
            scrollText?.text = message_two
        } else if (message_three != null) {
            scrollText?.text = message_three
        }

    }

    fun returnReply(view: View?) {
        val reply = mReply?.text.toString()
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        Log.d(LOG_TAG, "End SecondActivity")
        finish()
    }
}