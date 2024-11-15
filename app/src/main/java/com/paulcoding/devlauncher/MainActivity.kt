package com.paulcoding.devlauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.paulcoding.devlauncher.ui.page.AppEntry
import com.paulcoding.devlauncher.ui.page.ViewModel
import com.paulcoding.devlauncher.ui.theme.DevLauncherTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get()

        enableEdgeToEdge()
        setContent {
            DevLauncherTheme {
                AppEntry(viewModel = viewModel)
            }
        }
    }
}
