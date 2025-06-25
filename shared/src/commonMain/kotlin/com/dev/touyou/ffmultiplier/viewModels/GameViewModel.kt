package com.dev.touyou.ffmultiplier.viewModels

import com.dev.touyou.ffmultiplier.models.FNumber
import com.dev.touyou.ffmultiplier.models.GameModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.CoroutineDispatcher

class GameViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val gameModel = GameModel()
    private val viewModelScope = CoroutineScope(dispatcher + Job())
    private var timerJob: Job? = null

    private val _gameTime = MutableStateFlow(60)
    val gameTime: StateFlow<Int> = _gameTime.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _currentProblem = MutableStateFlow(gameModel.current)
    val currentProblem = _currentProblem.asStateFlow()

    private val _userInput = MutableStateFlow("")
    val userInput = _userInput.asStateFlow()

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver = _isGameOver.asStateFlow()

    init {
        startGame()
    }

    fun startGame() {
        gameModel.startGame()
        _currentProblem.value = gameModel.current
        _score.value = gameModel.currentScore
        _userInput.value = ""
        _isGameOver.value = false
        _gameTime.value = 60
        startTimer()
    }

    fun input(char: Char) {
        if (_userInput.value.length < 2) {
            _userInput.value += char.uppercase()
        }
    }

    fun delete() {
        _userInput.value = _userInput.value.dropLast(1)
    }

    fun submit() {
        if (_userInput.value.isEmpty()) return
        val answerValue = _userInput.value.toInt(16)
        gameModel.answerCurrentProblem(answerValue)
        _score.value = gameModel.currentScore
        _currentProblem.value = gameModel.current
        _userInput.value = ""
        if (gameModel.current == null) {
            finishGame()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_gameTime.value > 0) {
                delay(1000)
                _gameTime.value--
            }
            finishGame()
        }
    }

    private fun finishGame() {
        timerJob?.cancel()
        _isGameOver.value = true
    }
}