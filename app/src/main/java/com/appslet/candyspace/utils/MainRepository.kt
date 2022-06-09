package com.appslet.candyspace.utils

import retrofit2.Call

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getUsers(inName :String): Call<String> {
        return retrofitService.getUsers(20, "desc", "name", inName,"stackoverflow")
    }

}