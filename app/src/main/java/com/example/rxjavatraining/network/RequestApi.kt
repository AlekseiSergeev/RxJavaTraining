package com.example.rxjavatraining.network

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface RequestApi {

    @GET("todos/1")
    fun makeQuery(): Flowable<ResponseBody>
}