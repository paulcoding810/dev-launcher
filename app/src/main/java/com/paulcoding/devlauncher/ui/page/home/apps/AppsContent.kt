package com.paulcoding.devlauncher.ui.page.home.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paulcoding.devlauncher.ui.page.ViewModel

@Composable
fun AppsContent(viewModel: ViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    VerticalPager(
        state = rememberPagerState(
            initialPage = 0,
            pageCount = { 2 }
        )
    ) { page ->
        when (page) {
            0 -> MostUsedApps(
                modifier = Modifier.fillMaxSize(),
                apps = uiState.apps.subList(0, 5)
            )

            1 -> AllApps(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                apps = uiState.apps
            )
        }
    }
}
