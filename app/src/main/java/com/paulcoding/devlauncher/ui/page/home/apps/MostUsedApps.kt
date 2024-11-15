package com.paulcoding.devlauncher.ui.page.home.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paulcoding.devlauncher.ui.page.AppInfo

@Composable
fun MostUsedApps(modifier: Modifier, apps: List<AppInfo>) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(apps, key = { it.packageName }) { app ->
            AppView(modifier = Modifier.animateItem(), app = app)
        }
    }
}