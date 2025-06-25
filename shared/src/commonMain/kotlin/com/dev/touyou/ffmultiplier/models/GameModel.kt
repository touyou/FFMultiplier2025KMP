package com.dev.touyou.ffmultiplier.models

/**
 * Manages the state of the game.
 *
 * This class is responsible for:
 * - Storing and managing the list of problems.
 * - Tracking the current problem.
 * - Maintaining the player's score and combo.
 * - Handling game start and answer submission logic.
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

    /**
     * ゲームを開始する
     * - 問題リストを生成し、スコアとコンボを初期化する
     * - 最初の問題を設定する
     */
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
     * Attempts to answer the current problem.
     *
     * This function checks if the provided [answer] matches the answer to the [currentProblem].
     * If the answer is correct:
     * - The player's [score] is increased by [ScoreSetting.acceptedPoints] plus a bonus calculated by [ScoreSetting.calculateBonus] based on the current [combo].
     * - The [combo] count is incremented.
     * If the answer is incorrect:
     * - The player's [score] is increased by [ScoreSetting.failedPoints].
     * - The [combo] count is reset to 0.
     *
     * After processing the answer, the [currentProblem] is updated to the next problem in the list.
     *
     * @param answerValue The integer representing the player's answer.
     * @return `true` if the answer is correct, `false` otherwise. Returns `false` if there is no current problem.
     */
    fun answerCurrentProblem(answerValue: Int): Boolean {
        val problem = currentProblem ?: return false
        val isCorrect = problem.answer.value == answerValue
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