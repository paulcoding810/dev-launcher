package com.paulcoding.devlauncher.ui.page

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.paulcoding.devlauncher.MainApp.Companion.launcherApps
import com.paulcoding.devlauncher.MainApp.Companion.packageManager
import com.paulcoding.devlauncher.MainApp.Companion.userManager
import com.paulcoding.devlauncher.helper.log

class ViewModel : ViewModel() {

    fun getAllAppsByCategory() {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val list = packageManager.queryIntentActivities(intent, 0)
        log(list.size, "list size")
        list.forEach {
            println("${it.activityInfo.loadLabel(packageManager)} ${it.activityInfo.packageName}")
        }
    }

    fun getAllApps() = buildList {
        userManager.userProfiles.forEach { profile ->
            val activityList = launcherApps.getActivityList(null, profile)
            activityList.forEach {
                val applicationInfo = it.applicationInfo
                val name = applicationInfo.loadLabel(packageManager).toString()
                val icon = applicationInfo.loadIcon(packageManager)
                val packageName = applicationInfo.packageName
                val isSystem = applicationInfo.isSystemApp()
                log("$packageName $isSystem", name)
                add(AppItem(name, packageName, icon))
            }
        }
    }.sortedBy { it.name }

    data class AppItem(
        val name: String,
        val packageName: String,
        val icon: Drawable,
    )


    private fun ApplicationInfo.isSystemApp() = try {
        (flags and (ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0
    } catch (ex: PackageManager.NameNotFoundException) {
        false
    }
}