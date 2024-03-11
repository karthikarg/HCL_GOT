package com.hcl.got

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/*Represents the main application class for the application*/
@HiltAndroidApp
class GOTApplication : Application() {

    init {
        // Set the application instance when the application is created
        setInstance(this)
    }

    // Companion object to provide application-wide functionalities
    companion object {
        // Instance variable to hold the application instance
        private var instance: GOTApplication? = null

        // Function to set the application instance
        fun setInstance(application: GOTApplication) {
            instance = application
        }

        // Function to get the application context
        fun applicationContext(): Context {
            // Ensure that the instance is not null before accessing its properties
            return instance!!.applicationContext
        }
    }


}