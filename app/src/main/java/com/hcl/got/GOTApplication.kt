package com.hcl.got

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GOTApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }

    init {
        instance = this
    }

    companion object {
        private var instance: GOTApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}