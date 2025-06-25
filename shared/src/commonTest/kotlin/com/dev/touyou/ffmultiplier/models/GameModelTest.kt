package com.dev.touyou.ffmultiplier.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFalse

class GameModelTest {

    private val originalGenerateProblem = FFProblem.generateProblem

    @BeforeTest
    fun setup() {
        // テスト用の予測可能な問題生成ロジックを設定
        FFProblem.generateProblem = createPredictableProblemGenerator()
    }

    @AfterTest
    fun tearDown() {
        FFProblem.generateProblem = originalGenerateProblem
    }

    // テストヘルパーメソッド
    private fun createPredictableProblemGenerator(): (Int) -> List<FFProblem> = { count ->
        (1..count).map { i -> FFProblem(FNumber(i), FNumber(i)) }
    }

    private fun createGameModel(): GameModel = GameModel()

    companion object {
        private const val INITIAL_SCORE = 0
        private const val INITIAL_COMBO = 0
        private const val FIRST_COMBO = 1
        private const val MAX_HEX_VALUE = 255
    }

    @Test
    fun `startGame initializes problems and resets score and combo`() {
        // Given
        val gameModel = createGameModel()
        
        // When
        gameModel.startGame()

        // Then
        assertNotNull(gameModel.current)
        assertEquals(INITIAL_SCORE, gameModel.currentScore)
        assertEquals(INITIAL_COMBO, gameModel.currentCombo)
    }

    @Test
    fun `answerCurrentProblem with correct answer increases score and combo`() {
        // Given
        val gameModel = createGameModel()
        gameModel.startGame()
        val initialProblem = gameModel.current!!
        val correctAnswerValue = initialProblem.answer.value

        // When
        val isCorrect = gameModel.answerCurrentProblem(correctAnswerValue)

        // Then
        assertTrue(isCorrect)
        assertTrue(gameModel.currentScore > INITIAL_SCORE)
        assertEquals(FIRST_COMBO, gameModel.currentCombo)
        assertTrue(initialProblem != gameModel.current)
    }

    @Test
    fun `answerCurrentProblem with incorrect answer resets combo`() {
        // Given
        val gameModel = createGameModel()
        gameModel.startGame()
        
        // 最初に正解してコンボを増やす
        val problem1 = gameModel.current!!
        gameModel.answerCurrentProblem(problem1.answer.value)
        assertEquals(FIRST_COMBO, gameModel.currentCombo)

        // 次に不正解で回答
        val problem2 = gameModel.current!!
        val incorrectAnswerValue = MAX_HEX_VALUE // FF in hex. Assuming this is not the correct answer.

        // When
        val isCorrect = gameModel.answerCurrentProblem(incorrectAnswerValue)

        // Then
        assertFalse(isCorrect)
        assertEquals(INITIAL_COMBO, gameModel.currentCombo)
        assertTrue(problem2 != gameModel.current)
    }

    @Test
    fun `game ends after all problems are answered`() {
        // Given
        val gameModel = createGameModel()
        FFProblem.generateProblem = { (1..2).map { FFProblem(FNumber(it), FNumber(it)) } }
        gameModel.startGame()

        // When & Then
        assertNotNull(gameModel.current)
        gameModel.answerCurrentProblem(gameModel.current!!.answer.value)

        assertNotNull(gameModel.current)
        gameModel.answerCurrentProblem(gameModel.current!!.answer.value)

        assertNull(gameModel.current)
    }
}