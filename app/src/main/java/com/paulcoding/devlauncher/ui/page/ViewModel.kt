package com.paulcoding.devlauncher.ui.page

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.paulcoding.devlauncher.MainApp.Companion.appContext
import com.paulcoding.devlauncher.MainApp.Companion.launcherApps
import com.paulcoding.devlauncher.MainApp.Companion.packageManager
import com.paulcoding.devlauncher.MainApp.Companion.userManager
import com.paulcoding.devlauncher.helper.IPAddressMonitor
import com.paulcoding.devlauncher.helper.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(apps = getAllApps())
        }
    }

    private val ipAddressMonitor = IPAddressMonitor(appContext) { newIp ->
        if (newIp.isNotBlank()) {
            _uiState.update {
                it.copy(ip = newIp)
            }
        }
    }

    fun stopIPMonitoring() {
        ipAddressMonitor.stopMonitoring()
    }

    fun startIPMonitoring() {
        ipAddressMonitor.startMonitoring()
    }


    data class UiState(
        val ip: String = "",
        val apps: List<AppInfo> = listOf()
    )

    fun getAllAppsByCategory() {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val list = packageManager.queryIntentActivities(intent, 0)
        log(list.size, "list size")
        list.forEach {
            println("${it.activityInfo.loadLabel(packageManager)} ${it.activityInfo.packageName}")
        }
    }

    private fun getAllApps() = buildSet {
        val packageNameSet = mutableSetOf<String>()
        userManager.userProfiles.forEach { profile ->
            val activityList = launcherApps.getActivityList(null, profile)
            activityList.forEach {
                val applicationInfo = it.applicationInfo
                val name = applicationInfo.loadLabel(packageManager).toString()
                val icon = applicationInfo.loadIcon(packageManager)
                val packageName = applicationInfo.packageName
                val isSystem = applicationInfo.isSystemApp()

                if (!packageNameSet.contains(packageName)) {
                    packageNameSet.add(packageName)
                    add(AppInfo(name, packageName, icon, isSystem))
                }
            }
        }
    }.sortedBy { it.name }


    private fun ApplicationInfo.isSystemApp() = try {
        (flags and (ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0
    } catch (ex: PackageManager.NameNotFoundException) {
        false
    }
}

data class AppInfo(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    val isSystem: Boolean,
) {
    fun open() {
        packageManager.getLaunchIntentForPackage(packageName)?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            appContext.startActivity(it)
        }
    }
}
