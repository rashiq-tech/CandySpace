package com.appslet.candyspace.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appslet.candyspace.model.Tags
import com.appslet.candyspace.model.User
import com.appslet.candyspace.utils.MainRepository
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val usersList = MutableLiveData<List<User>>()
    val topTagList = MutableLiveData<List<Tags>>()
    val errorMessage = MutableLiveData<String>()

    var job: Job? = null

    //to get tall users list with 20 limit, ordered in desc order based on name
    fun getUsersList(inName: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getUsers(inName)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val jsonResponse = JSONObject(response.body().toString())
                        val jsonData = jsonResponse.getJSONArray("items")

                        usersList.postValue(
                            Gson().fromJson(jsonData.toString(), Array<User>::class.java)
                                .toList()
                        )
                    } else {
                        errorMessage.postValue(response.message())
                    }
                } else {
                    errorMessage.postValue(response.message())
                }
            }
        }
    }

    //to get top tags against user account_id
    fun getTopTags(id: Int) {

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getTopTags(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val jsonResponse = JSONObject(response.body().toString())
                        val jsonData = jsonResponse.getJSONArray("items")

                        topTagList.postValue(
                            Gson().fromJson(jsonData.toString(), Array<Tags>::class.java)
                                .toList()
                        )
                    } else {
                        errorMessage.postValue(response.message())
                    }
                } else {
                    errorMessage.postValue(response.message())
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}