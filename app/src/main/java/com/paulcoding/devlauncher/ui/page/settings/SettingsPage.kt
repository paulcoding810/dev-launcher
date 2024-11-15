package com.paulcoding.devlauncher.ui.page.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paulcoding.devlauncher.ui.page.ViewModel

@Composable
fun SettingsPage(viewModel: ViewModel, goBack: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {}) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Yellow)
        ) {
            // content
        }
    }
}