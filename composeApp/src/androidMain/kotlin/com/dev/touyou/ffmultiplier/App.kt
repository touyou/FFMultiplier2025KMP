package com.dev.touyou.ffmultiplier

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dev.touyou.ffmultiplier.components.RankingList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import ffmultiplier2025.composeapp.generated.resources.Res
import ffmultiplier2025.composeapp.generated.resources.compose_multiplatform

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            contentWindowInsets = WindowInsets.systemBars
        ) { innerPadding ->
            RankingList(
                innerPadding = innerPadding
            )
        }
    }
}