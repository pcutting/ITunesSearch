package com.philipcutting.itunessearch.utils

import android.util.Log
import com.philipcutting.itunessearch.models.StoreItem
import com.philipcutting.itunessearch.models.StoreResultsList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

const val TAG = "Utilities.kt"

/*  Sample:
    var queryMap : Map<String,String> = mapOf(
        "term" to "red+velvet",
        "media" to "music",
        "limit" to "500"
    )
 */

fun URL.buildUrl(queryMap: Map<String,String>):URL {
    var oldUrl : URL = this
    var query = queryMap.map {
        "${it.key}=${it.value.replace(' ', '+')}"
    }.joinToString("&")
    val uri = "$oldUrl?$query"
    val url = URL(uri)
    Log.i(TAG, " \"URL -> ${url}, path -> ${url}, file -> ${url.file},query -> ${url.query}")
    return url
}

fun fetchItems(query: Map<String,String>, onComplete: (MutableList<StoreItem?>?) -> Unit) {
    val permittedQueryKeys = listOf(
        "term", "country", "media", "entity", "attribute",
        "callback", "limit", "lang", "version", "explicit"
    )

    val validQuery = mutableMapOf<String, String>()
    query.forEach { (key, value) ->
        if (!permittedQueryKeys.contains(key) && value != "") {
            Log.e(TAG, "Invalid search parameter passed to search query: $key")
        } else {
            validQuery[key] = value
        }
    }

    val base = "https://itunes.apple.com/search?"
    var url = URL(base)
    url = url.buildUrl(validQuery)
    var data: String?
    CoroutineScope(Dispatchers.IO).launch {
        data = getItunesJson(url)
        try {
            val json = JSONObject(data)
            val store = StoreResultsList(json)
            onComplete(store.results)
        } catch (ex: java.lang.Exception) {
            Log.e(TAG, "unable to perform search, check your search criteria: Error $ex")
        }
    }
}

private fun streamToString(inputStream: InputStream): String {
    val bufferReader = BufferedReader(InputStreamReader(inputStream))
    var line: String
    var result = ""
    try {
        result = bufferReader.readText()
            .filter{
                it != '\n'
            }
        inputStream.close()
    } catch (ex: Exception) {
        Log.i(TAG, "exception in streamToString: $ex")
    }
    return result
}

fun getItunesJson(url: URL):String? {
    var data = ""
    try {
        var connection = url.openConnection() as HttpsURLConnection
        connection.connect()
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        data = streamToString(connection.inputStream)
    } catch (ex: Exception) {
        Log.i (TAG, "Exception : $ex")
    }

    return if (data == "") {
        null
    } else {
        data
    }
}