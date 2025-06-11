package com.dev.touyou.ffmultiplier

/**
 * ゲームの状態を管理するクラス
 */
class GameModel {
    private var problems: MutableList<FFProblem> = mutableListOf()
    private var currentProblem: FFProblem? = null
    private var score: Int = 0
    private var combo: Int = 0

    val current: FFProblem?
        get() = currentProblem

    val currentScore: Int
        get() = score

    val currentCombo: Int
        get() = combo

    fun startGame() {
        problems = FFProblem.generateProblem(1000).toMutableList()
        score = 0
        combo = 0
        currentProblem = pickNextProblem()
    }

    private fun pickNextProblem(): FFProblem? {
        return if (problems.isNotEmpty()) {
            problems.removeAt(0)
        } else {
            null
        }
    }

    /**
     * 回答を行う
     */
    fun answerCurrentProblem(answer: FNumber): Boolean {
        val problem = currentProblem ?: return false
        val isCorrect = problem.answer == answer
        if (isCorrect) {
            score += ScoreSetting.acceptedPoints + ScoreSetting.calculateBonus(combo)
            combo += 1
        } else {
            score += ScoreSetting.failedPoints
            combo = 0
        }
        currentProblem = pickNextProblem()
        return isCorrect
    }
}