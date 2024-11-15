package com.paulcoding.devlauncher.ui.page.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paulcoding.devlauncher.ui.page.ViewModel
import com.paulcoding.devlauncher.ui.page.home.apps.AppsContent

@Composable
fun HomePage(viewModel: ViewModel, navToSettings: () -> Unit) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {}) {
        HorizontalPager(
            state = rememberPagerState { 2 },
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) { page ->
            when (page) {
                0 -> AppsContent(viewModel)
                1 -> SpecsContent(viewModel)
            }
        }
    }
}