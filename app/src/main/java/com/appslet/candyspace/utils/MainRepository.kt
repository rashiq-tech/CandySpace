package com.appslet.candyspace.utils

import retrofit2.Call
import retrofit2.Response

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getUsers(inName :String): Response<String> {
        return retrofitService.getUsers(20, "desc", "name", inName,"stackoverflow")
    }

    suspend fun getTopTags(id :Int): Response<String> {
        return retrofitService.getTopTags(id, 5,"stackoverflow")
    }

}