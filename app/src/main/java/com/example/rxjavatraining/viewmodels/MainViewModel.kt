package com.example.rxjavatraining.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rxjavatraining.repository.Repository
import okhttp3.ResponseBody

class MainViewModel : ViewModel() {

    private val repository = Repository().getInstance()

    fun makeQuery(): LiveData<ResponseBody> {
        return repository?.makeReactiveQuery()!!
    }
}