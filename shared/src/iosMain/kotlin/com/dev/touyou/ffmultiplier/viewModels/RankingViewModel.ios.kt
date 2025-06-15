package com.dev.touyou.ffmultiplier.viewModels

import androidx.lifecycle.viewModelScope
import com.dev.touyou.ffmultiplier.models.Score
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


fun RankingViewModel.observeScores(onNewScores: (List<Score>) -> Unit): Job {
    return viewModelScope.launch {
        scores.collect { scoreList -> // Use a more descriptive lambda parameter name
            onNewScores(scoreList)
        }
    }
}