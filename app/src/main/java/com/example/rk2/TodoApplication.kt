package com.example.rk2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Hilt dependency injection.
 * This must be annotated with @HiltAndroidApp for Hilt to work.
 */
@HiltAndroidApp
class TodoApplication : Application()

