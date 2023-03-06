package com.epitech.soraeven

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private var context: Context? = null
        val staticContext: Context?
            get() = context
    }
}
/* This is for access the shared preferences everywhere, even in classes where there is not
 a context, like RedditClient.kt
 This also need to add 'android:name=".MyApplication"' in <application> of AndroidManifest.xml
*/