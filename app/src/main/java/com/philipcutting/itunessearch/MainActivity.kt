package com.philipcutting.itunessearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.philipcutting.itunessearch.models.StoreItem
import com.philipcutting.itunessearch.models.StoreResultsList
import com.philipcutting.itunessearch.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

/*
Create an HttpsURLConnection.
Use the run extension function to set up the request, connect to the endpoint and get the data from the endpoint.
Once the data has been retrieved, print it to the console.
 */

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queryMap : Map<String,String> = mapOf(
            "term" to "red+velvet",
            "media" to "musicq",
            "limit" to "500"
        )

        fetchItems(queryMap){
            it?.forEach { item ->
                Log.d(TAG, "'${item?.trackName}' by '${item?.artistName}'")
            }

        }





    }
}