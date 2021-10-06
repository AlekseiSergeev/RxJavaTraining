package com.example.rxjavatraining.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rxjavatraining.R
import com.example.rxjavatraining.models.Task
import com.example.rxjavatraining.repository.DataSource
import com.example.rxjavatraining.viewmodels.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.makeQuery().observe(this, Observer {
            Log.i(TAG, "onChange: This is LiveData response")
            try {
                Log.i(TAG, "onChange: ${it.string()}")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })

        var taskObservable: Observable<Task> = Observable
            .fromIterable(DataSource().createTasksList())
            .subscribeOn(Schedulers.io())
            .filter(object : Predicate<Task> {
                override fun test(task: Task): Boolean {
                    return task.isComplete
                }
            })
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : io.reactivex.rxjava3.core.Observer<Task> {
            override fun onSubscribe(d: Disposable?) {
                Log.i(TAG, "onSubscribe: called.")
            }

            override fun onNext(task: Task?) {
                Log.i(TAG, "onNext: " + Thread.currentThread().name)
                Log.i(TAG, "onNext: " + task?.description)
            }

            override fun onError(e: Throwable?) {
                Log.e(TAG, "onError: ", e)
            }

            override fun onComplete() {
                Log.i(TAG, "onComplete: called.")
            }
        })
    }
}