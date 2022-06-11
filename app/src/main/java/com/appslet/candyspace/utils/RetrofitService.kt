package com.appslet.candyspace.utils

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*


interface RetrofitService {

    @GET("users")
    suspend fun getUsers(
        @Query("pagesize") pageSize: Int,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("inname") inName: String,
        @Query("site") site: String
    ): Response<String>

    @GET("users/{id}/top-tags")
    suspend fun getTopTags(
        @Path(value = "id") id: Int,
        @Query("pagesize") pageSize: Int,
        @Query("site") site: String
    ): Response<String>


    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.stackexchange.com/2.3/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }

}