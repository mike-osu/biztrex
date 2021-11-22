package edu.oregonstate.biztrex

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for HTTP API requests
 */
interface ApiInterface {

    @GET("/results")
    fun getBusinesses(@Query("term") searchTerm: String, @Query("location") location: String) : Call<List<ApiResponseItem>>

    @GET("/results")
    fun getBusinessesByLatLong(@Query("term") searchTerm: String,
                               @Query("latitude") latitude: Double,
                               @Query("longitude") longitude: Double) : Call<List<ApiResponseItem>>

    companion object {

        /**
         * Deployed teammate's service with instructor's approval
         * https://edstem.org/us/courses/14495/discussion/799922
         */
        var BASE_URL = "http://macosta.pythonanywhere.com/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}