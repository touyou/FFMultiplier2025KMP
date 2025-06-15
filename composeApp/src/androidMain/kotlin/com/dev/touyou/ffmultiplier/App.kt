package com.dev.touyou.ffmultiplier

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.dev.touyou.ffmultiplier.components.RankingList
import org.jetbrains.compose.ui.tooling.preview.Preview

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