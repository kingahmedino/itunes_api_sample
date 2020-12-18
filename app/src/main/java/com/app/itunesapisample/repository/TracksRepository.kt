package com.app.itunesapisample.repository

import com.app.itunesapisample.api.Backend
import com.app.itunesapisample.api.TrackResponse
import com.app.itunesapisample.models.Track
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

object TracksRepository {

    suspend fun searchItem(item: String): List<Track>? {
        val url = Backend.BASE_URL + "search?term=" + item
        val response = Backend.invoke().search(url)
        val jsonString = getJSONDataFrom(response)
        val trackResponse = parse(jsonString)
        return trackResponse?.results
    }

    private fun getJSONDataFrom(response: Response<ResponseBody>): String? {
        if (response.body() != null) {
            val inputStream = response.body()!!.byteStream()
            return inputStream.bufferedReader().use { it.readText() }
        }
        return null
    }

    private fun parse(jsonString: String?): TrackResponse? {
        if (jsonString != null) {
            val gson = Gson()
            return gson.fromJson(jsonString, TrackResponse::class.java)
        }
        return null
    }
}