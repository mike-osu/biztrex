package edu.oregonstate.biztrex

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/results")
    fun getBusinesses(@Query("term") searchTerm: String, @Query("location") location: String) : Call<List<ApiResponseItem>>

    companion object {

        var BASE_URL = "http://10.0.2.2:5000/"  // Special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine)

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}