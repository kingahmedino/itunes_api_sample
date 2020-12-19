package com.app.itunesapisample.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "json_table")
data class JSONData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "search_item")
    val searchItem: String = "",
    @ColumnInfo(name = "json")
    val jsonString: String = ""
)