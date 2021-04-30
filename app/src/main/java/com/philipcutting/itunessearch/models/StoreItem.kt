package com.philipcutting.itunessearch.models

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

//TODO:  the following requirement didn't make sense.  I didn't see this happen. Old api?
/*

You might have noticed that an item can have different types of descriptions. Some items have a description key, while others have a longDescription key. Your model object makes no distinction between the two. If the API sends down a value with the description key, that is what you should assign to your model. However, if it sends down a value with the longDescription key and nothing with the description key, you should use that instead. If neither value exists, just assign description to an empty string.

 */

private const val TAG =  "Model - StoreItem"

data class StoreResultsList(
    var count: Int = 0,
    var results: MutableList<StoreItem?> = mutableListOf()
){

    constructor(json: JSONObject) : this(){
        withJSON(json)
    }

    private fun withJSON(json: JSONObject) {
        Log.i(TAG, "resultCount: ${json["resultCount"]}")
        count = json["resultCount"].toString().toIntOrNull() ?: 0
        val searchResults: JSONArray = ( json["results"] as JSONArray)
        (0 until searchResults.length()).forEach {
            results.add(StoreItem(searchResults.getJSONObject(it)))
        }
        Log.i(TAG, "results.count = ${results.count()}")
    }
}

data class StoreItem(
    var wrapperType: String = "",
    var kind: String = "",
    var artistId: Long = -1,
    var trackId: Long = -1,
    var artistName: String = "",
    var trackName: String = "",
    var trackCensoredName: String = "",
    var artistViewUrl: String = "",
    var trackViewUrl: String = "",
    var previewUrl: String = "",
    var artworkUrl30: String = "",
    var artworkUrl60: String = "",
    var artworkUrl100: String = "",
    var collectionPrice: Double = -1.0,
    var trackPrice: Double = -1.0,
    var releaseDate: String = "",
    var collectionExplicitness: String = "",
    var trackExplicitness: String = "",
    var trackTimeMillis: Long = -1,
    var country: String = "",
    var currency: String = "",
    var primaryGenreName: String = "",
) {
    constructor(json: JSONObject) : this(){
        withJSON(json)
    }

    fun withJSON(json: JSONObject) {
        wrapperType  = json["wrapperType"].toString()
        kind= json["kind"].toString()
        artistId= json["artistId"].toString().toLong()
        trackId= json["trackId"].toString().toLong()
        artistName= json["artistName"].toString()
        trackName= json["trackName"].toString()
        trackCensoredName= json["trackCensoredName"].toString()
        artistViewUrl= json["artistViewUrl"].toString()
        trackViewUrl= json["trackViewUrl"].toString()
        previewUrl= json["previewUrl"].toString()
        artworkUrl30= json["artworkUrl30"].toString()
        artworkUrl60= json["artworkUrl60"].toString()
        artworkUrl100= json["artworkUrl100"].toString()
        collectionPrice= json["collectionPrice"].toString().toDouble()
        trackPrice= json["trackPrice"].toString().toDouble()
        releaseDate= json["releaseDate"].toString()
        collectionExplicitness= json["collectionExplicitness"].toString()
        trackExplicitness= json["trackExplicitness"].toString()
        trackTimeMillis= json["trackTimeMillis"].toString().toLong()
        country= json["country"].toString()
        currency= json["currency"].toString()
        primaryGenreName = json["primaryGenreName"].toString()
    }
}
