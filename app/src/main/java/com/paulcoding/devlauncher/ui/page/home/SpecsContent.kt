package com.paulcoding.devlauncher.ui.page.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.paulcoding.devlauncher.ui.page.ViewModel


@Composable
fun SpecsContent(viewModel: ViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        viewModel.startIPMonitoring()
        onDispose {
            viewModel.stopIPMonitoring()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Row {
            Text("Ip", modifier = Modifier.weight(1f))
            Text(uiState.ip)
        }
    }
}