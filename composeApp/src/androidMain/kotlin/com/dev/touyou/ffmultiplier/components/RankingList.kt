package com.dev.touyou.ffmultiplier.components

import android.os.Build
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.dev.touyou.ffmultiplier.viewModels.RankingViewModel
import org.koin.compose.koinInject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RankingList(
    innerPadding: PaddingValues
) {
    val viewModel: RankingViewModel = koinInject()
    LaunchedEffect(Unit) {
        viewModel.startObservingScores()
    }
    val scores by viewModel.scores.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(all= Dp(16F))
    ) {
        items(scores) { score ->
            Row {
                Text(
                    text = score.score.toString(),
                )
                Text(
                    text = score.userRef,
                )
            }
        }
    }
}