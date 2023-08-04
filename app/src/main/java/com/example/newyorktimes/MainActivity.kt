package com.example.newyorktimes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.newyorktimes.ApiResponse



class MainActivity : AppCompatActivity() {

    lateinit var rvMain: RecyclerView
    lateinit var myAdapter: MyAdapter
    var BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
    var newsItemList = mutableListOf<NewsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMain = findViewById(R.id.recyclerView)
        // Initialize the RecyclerView adapter
        myAdapter = MyAdapter(this, newsItemList)
        rvMain.adapter = myAdapter
        // Set the LinearLayoutManager
        rvMain.layoutManager = LinearLayoutManager(this)
        getAllData()
    }

    private fun getAllData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val retroData = retrofit.getData("zzep3lI3QeKuFGjs30jE1X6QTXv1w5Yz")
        retroData.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val apiResponse = response.body()

                if (apiResponse != null) {
                    val newsItems = apiResponse.results
                    Log.d("APIResponse", newsItems.toString())

                    newsItemList.clear()
                    newsItemList.addAll(newsItems)
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("RetrofitError", t.message ?: "Unknown error occurred")
            }
        })
    }
}
