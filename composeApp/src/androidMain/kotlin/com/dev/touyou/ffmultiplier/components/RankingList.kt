package com.dev.touyou.ffmultiplier.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.touyou.ffmultiplier.viewModels.RankingViewModel
import org.koin.compose.koinInject

@Composable
fun RankingList() {
    val viewModel: RankingViewModel = koinInject()
    
    LaunchedEffect(Unit) {
        viewModel.startObservingScores()
    }
    
    val scores by viewModel.scores.collectAsState()
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        items(scores) { score ->
            RankingItem(
                score = score.score,
                userRef = score.userRef
            )
        }
    }
}

@Composable
private fun RankingItem(
    score: Int,
    userRef: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = score.toString(),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = userRef,
            modifier = Modifier.weight(2f)
        )
    }
}