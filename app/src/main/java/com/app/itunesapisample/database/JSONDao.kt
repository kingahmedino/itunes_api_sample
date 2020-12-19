package com.app.itunesapisample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.itunesapisample.models.JSONData

@Dao
interface JSONDao {
    @Insert
    fun insert(json: JSONData)

    @Query("SELECT * FROM json_table WHERE search_item = :query")
    fun getSearchQueryFromDB(query: String) : JSONData

    @Query("SELECT * FROM json_table ORDER BY id DESC LIMIT 1")
    fun getLastSearchQuery(): JSONData?
}