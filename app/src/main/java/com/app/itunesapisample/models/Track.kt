package com.app.itunesapisample.models

data class Track(
    val trackId: Int,
    val artistName: String,
    val collectionName: String,
    val trackName: String,
    val artworkUrl100: String
)