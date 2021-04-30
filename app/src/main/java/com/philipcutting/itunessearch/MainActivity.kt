package com.philipcutting.itunessearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.philipcutting.itunessearch.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

/*
Create an HttpsURLConnection.
Use the run extension function to set up the request, connect to the endpoint and get the data from the endpoint.
Once the data has been retrieved, print it to the console.
 */

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var queryMap : Map<String,String> = mapOf(
            "term" to "red+velvet",
            "media" to "music",
            "limit" to "500"
        )

        val base = "https://itunes.apple.com/search?"
        var url = URL(base)
        url = url.buildUrl(queryMap)
        CoroutineScope(Dispatchers.IO).launch {
                Log.i(TAG,"getItunesJson:  ${getItunesJson(url) ?: "NULL"}")
            }
    }
}