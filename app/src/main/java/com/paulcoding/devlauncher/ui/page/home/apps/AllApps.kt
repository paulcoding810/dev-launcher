package com.paulcoding.devlauncher.ui.page.home.apps

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.paulcoding.devlauncher.ui.page.AppInfo

@Composable
fun AllApps(modifier: Modifier, apps: List<AppInfo>, focused: Boolean) {
    val focusManager = LocalFocusManager.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf("") }
    var currentApps by remember { mutableStateOf(apps) }

    fun filterApps(query: String) {
        currentApps = if (query.isEmpty())
            apps
        else
            apps.filter { app -> app.name.lowercase().contains(query.lowercase()) }
    }

    fun clearSearch() {
        text = ""
        focusManager.clearFocus()
        softwareKeyboardController?.hide()
    }

    LaunchedEffect(focused) {
        if (!focused)
            clearSearch()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, CircleShape),
            shape = CircleShape,
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = {
                        clearSearch()
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            },
            placeholder = { Text("Search") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            value = text, onValueChange = {
                text = it
                filterApps(it)
            })
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(currentApps, key = { it.packageName }) { app ->
                AppView(modifier = Modifier.animateItem(), app = app)
            }
        }
    }
}