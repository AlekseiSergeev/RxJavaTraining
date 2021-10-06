package com.example.rxjavatraining.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.rxjavatraining.network.ServiceGenerator
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody


class Repository {

    private var instance: Repository? = null

    fun getInstance(): Repository? {
        if (instance == null) {
            instance = Repository()
        }
        return instance
    }

    fun makeReactiveQuery(): LiveData<ResponseBody> {
        val serviceGenerator = ServiceGenerator()
        return LiveDataReactiveStreams.fromPublisher(
            serviceGenerator.getRequestApi()
                .makeQuery()
                .subscribeOn(Schedulers.io())
        )
    }
}