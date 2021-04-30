package com.philipcutting.itunessearch.utils

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

val TAG = "Utilities.kt"

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

private fun streamToString(inputStream: InputStream): String {
    val bufferReader = BufferedReader(InputStreamReader(inputStream))
    var line: String
    var result = ""
    try {
        do {
            line = bufferReader.readLine()
            if (line != null) {
                result += line
            }
        } while (line != null)
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