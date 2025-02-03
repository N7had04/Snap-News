package com.example.mysimpleapp

import android.app.Application
import com.example.mysimpleapp.data.Repository
import com.example.mysimpleapp.network.Api
import com.example.mysimpleapp.network.NewsManager

class MainApp: Application() {
    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }
    val repository by lazy {
        Repository(manager = manager)
    }
}