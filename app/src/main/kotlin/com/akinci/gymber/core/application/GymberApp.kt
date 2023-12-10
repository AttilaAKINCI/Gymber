package com.akinci.gymber.core.application

import android.app.Application
import com.akinci.gymber.core.logger.LoggerInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GymberApp : Application() {
    @Inject
    lateinit var loggerInitializer: LoggerInitializer

    override fun onCreate() {
        super.onCreate()

        // initialize timber trees
        loggerInitializer.initialize()
    }
}
