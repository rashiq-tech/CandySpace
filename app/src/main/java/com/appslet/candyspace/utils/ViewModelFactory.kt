package com.appslet.candyspace.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appslet.candyspace.viewmodel.MainViewModel

class ViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(this.repository) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}