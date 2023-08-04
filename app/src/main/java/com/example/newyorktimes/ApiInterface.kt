package com.example.newyorktimes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("home.json")
    fun getData(@Query("api-key") token: String): Call<ApiResponse>
}

data class ApiResponse(
    val status: String,
    val results: List<NewsItem>
)
