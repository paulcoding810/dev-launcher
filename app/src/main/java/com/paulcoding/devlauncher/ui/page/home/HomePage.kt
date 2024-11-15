package com.paulcoding.devlauncher.ui.page.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.paulcoding.devlauncher.ui.page.ViewModel

@Composable
fun HomePage(viewModel: ViewModel, navToSettings: () -> Unit) {
    val apps = viewModel.getAllApps()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {}) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                apps.map { app ->
                    val imageBitmap = rememberAsyncImagePainter(app.icon)
                    Row {
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
            }
        }
    }
}