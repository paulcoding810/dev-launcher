package com.paulcoding.devlauncher.ui.page.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.paulcoding.devlauncher.ui.page.AppInfo
import com.paulcoding.devlauncher.ui.page.ViewModel

@Composable
fun AppsContent(viewModel: ViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.apps, key = { it.packageName }) { app ->
                AppView(modifier = Modifier.animateItem(), app = app)
            }
        }
    }
}

@Composable
fun AppView(modifier: Modifier = Modifier, app: AppInfo) {
    val imageBitmap = rememberAsyncImagePainter(app.icon)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                app.open()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = imageBitmap,
            "icon",
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        )
        Text(app.name)
    }
}