package com.hcl.got

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GOTApplication : Application() {

    init {
        GOTApplication.instance = this
    }

    companion object {
        var instance: GOTApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}