package com.app.itunesapisample.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Backend {
    companion object {
        private const val BASE_URL = "https://itunes.apple.com/search?term="

        operator fun invoke() : Backend{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Backend::class.java)
        }
    }

    @GET("{item}")
    fun searchFor(item: String) : Call<ResponseBody>
}