package com.dev.touyou.ffmultiplier.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.dev.touyou.ffmultiplier.viewModels.GameViewModel

import androidx.compose.material3.Surface

@Composable
fun GameScreen() {
    val viewModel = remember { GameViewModel() }
    val time by viewModel.gameTime.collectAsState()
    val score by viewModel.score.collectAsState()
    val problem by viewModel.currentProblem.collectAsState()
    val userInput by viewModel.userInput.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (!isGameOver) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                GameHeader(time = time, score = score)
                ProblemDisplay(problem = problem, userInput = userInput)
                HexKeypad(
                    onInput = { viewModel.input(it) },
                    onDelete = { viewModel.delete() },
                    onSubmit = { viewModel.submit() }
                )
            }
        } else {
            // TODO: Game Over Screen
        }
    }
}