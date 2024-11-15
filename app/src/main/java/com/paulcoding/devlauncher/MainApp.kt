package com.paulcoding.devlauncher

import android.app.Application
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.UserManager
import androidx.core.content.getSystemService

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        launcherApps = getSystemService()!!
        userManager = getSystemService()!!
        MainApp.packageManager = this.packageManager
    }

    companion object {
        lateinit var appContext: Context
        lateinit var packageManager: PackageManager
        lateinit var launcherApps: LauncherApps
        lateinit var userManager: UserManager
    }
}