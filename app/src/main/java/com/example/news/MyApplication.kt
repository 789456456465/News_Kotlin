package com.example.news

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class MyApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
        val Key = "4b2055129172260a7f642e28bfb97d6c"
    }

    override fun onCreate() {
        super.onCreate()
        context = baseContext
    }
}