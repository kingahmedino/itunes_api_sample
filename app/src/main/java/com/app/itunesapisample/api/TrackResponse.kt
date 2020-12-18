package com.app.itunesapisample.api

import com.app.itunesapisample.models.Track

data class TrackResponse(
    val results: List<Track>
)