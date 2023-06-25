package com.example.shorymovies

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * ShoryApplication extends application, whole app has at least single application class,
 *
 * We will be using our custom to have dependency injection (DI)
 *
 * using DI in app, we must use our own application class in manifest
 */
@HiltAndroidApp
class ShoryApplication : Application()