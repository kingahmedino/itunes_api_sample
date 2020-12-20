package com.app.itunesapisample.repository

import android.app.Application
import com.app.itunesapisample.api.Backend
import com.app.itunesapisample.api.TrackResponse
import com.app.itunesapisample.database.JSONDao
import com.app.itunesapisample.database.JSONDatabase
import com.app.itunesapisample.models.JSONData
import com.app.itunesapisample.models.Track
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

object TracksRepository {
    private lateinit var dao: JSONDao

    fun initDao(application: Application) {
        dao = JSONDatabase.getInstance(application).jsonDao
    }

    suspend fun searchItem(item: String): List<Track>? {
        val url = Backend.BASE_URL + "search?term=" + item
        val response = Backend.invoke().search(url)
        val jsonString = getJSONDataFrom(response)
        if (!jsonString.isNullOrEmpty()){
            val jsonData = JSONData(searchItem = item, jsonString = jsonString)
            insert(jsonData)
        }
        return parse(jsonString)?.results
    }

    suspend fun searchItemInDatabase(query: String): List<Track>? {
        val jsonDataFromDB = withContext(Dispatchers.IO) {
            dao.getSearchQueryFromDB(query)
        }
        return if (jsonDataFromDB != null) {
            parse(jsonDataFromDB.jsonString)?.results
        } else {
            null
        }
    }

    suspend fun getLastDBQuery(): List<Track>? {
        return withContext(Dispatchers.IO) {
            val lastQuery = dao.getLastSearchQuery()
            if (lastQuery != null) {
                parse(lastQuery.jsonString)?.results
            } else {
                null
            }
        }
    }

    suspend fun insert(jsonData: JSONData) {
        withContext(Dispatchers.IO) {
            dao.insert(jsonData)
        }
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