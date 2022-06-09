package com.appslet.candyspace.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appslet.candyspace.model.User
import com.appslet.candyspace.utils.MainRepository
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val usersList = MutableLiveData<List<User>>()
    val errorMessage = MutableLiveData<String>()

    fun getUsersList(inName : String){
        val response = repository.getUsers(inName)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                val jsonResponse  = JSONObject(response.body().toString())
                val jsonData  = jsonResponse.getJSONArray("items")

                usersList.postValue(
                    Gson().fromJson(jsonData.toString(), Array<User>::class.java)
                    .toList())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}