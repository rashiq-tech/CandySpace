package com.appslet.candyspace.utils

import retrofit2.Call

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getUsers(inName :String): Call<String> {
        return retrofitService.getUsers(20, "desc", "name", inName,"stackoverflow")
    }

    fun getTopTags(id :Int): Call<String> {
        return retrofitService.getTopTags(id, 5,"stackoverflow")
    }

}