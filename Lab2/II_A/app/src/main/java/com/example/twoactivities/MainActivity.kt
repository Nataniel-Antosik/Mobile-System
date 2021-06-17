package com.example.twoactivities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat.IntentBuilder

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.simpleName
    val EXTRA_MESSAGE = "com.example.twoactivities.extra.MESSAGE"
    val EXTRA_MESSAGE_ONE = "com.example.twoactivities.extra.MESSAGE_ONE"
    val EXTRA_MESSAGE_TWO = "com.example.twoactivities.extra.MESSAGE_TWO"
    val EXTRA_MESSAGE_THREE = "com.example.twoactivities.extra.MESSAGE_THREE"
    private var mMessageEditText: EditText? = null
    var TEXT_REQUEST = 1
    private var mReplyHeadTextView: TextView? = null
    private var mReplyTextView: TextView? = null
    private var mWebsiteEditText: EditText? = null
    private var mLocationEditText: EditText? = null
    private var mShareTextEditText: EditText? = null
    private var textView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")

        mMessageEditText = findViewById<EditText>(R.id.editText_main)
        mReplyHeadTextView = findViewById<TextView>(R.id.text_header_reply)
        mReplyTextView = findViewById<TextView>(R.id.text_message_reply)
        mWebsiteEditText = findViewById(R.id.website_edittext);
        mLocationEditText = findViewById(R.id.location_edittext);
        mShareTextEditText = findViewById(R.id.share_edittext);

        if (savedInstanceState != null) {
            val isVisible = savedInstanceState.getBoolean("reply_visible")
            if (isVisible) {
                mReplyHeadTextView?.setVisibility(View.VISIBLE)
                mReplyTextView?.setText(savedInstanceState.getString("reply_text"))
                mReplyTextView?.setVisibility(View.VISIBLE);
            }
        }
        val intent = getIntent()
        val uri = intent?.getData()
        if (uri != null) {
            val uri_string = "URI: " + uri.toString()
            textView = findViewById(R.id.text_uri_message)
            textView?.setText(uri_string);
        }
    }

    fun openWebsite(view: View?) {
        // Get the URL text.
        val url: String = mWebsiteEditText?.text.toString()

        // Parse the URI and create the intent.
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)

        // Find an activity to hand the intent and start that activity.
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!")
        }
    }

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

    fun shareText(view: View?) {
        val txt = mShareTextEditText!!.text.toString();
        val mimeType = "text/plain";
        IntentBuilder.from(this)
            .setType(mimeType)
            .setChooserTitle(getString(R.string.share_text_with))
            .setText(txt)
            .startChooser()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState!!)
        if (mReplyHeadTextView?.visibility == View.VISIBLE) {
            outState.putBoolean("reply_visible", true)
            outState.putString("reply_text",mReplyTextView?.text.toString());
        }
    }

   override fun onStart(){
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    override fun onPause(){
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onRestart(){
        super.onRestart()
        Log.d(LOG_TAG, "onRestart")
    }

    override fun onResume(){
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    override fun onStop(){
        super.onStop()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }

    fun launchSecondActivity(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        val message = mMessageEditText?.text.toString()
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    fun launchSecondActivityTextOne(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE_ONE, resources.getString(R.string.text_one))
        startActivity(intent)
    }

    fun launchSecondActivityTextTwo(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE_TWO,resources.getString(R.string.text_two))
        startActivity(intent)
    }

    fun launchSecondActivityTextThree(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE_THREE,resources.getString(R.string.text_three))
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                val reply = data?.getStringExtra(SecondActivity().EXTRA_REPLY)
                mReplyHeadTextView?.setVisibility(View.VISIBLE)
                mReplyTextView?.setText(reply)
                mReplyTextView?.setVisibility(View.VISIBLE)
            }
        }
    }
}
