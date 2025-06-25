package com.dev.touyou.ffmultiplier.viewModels

import com.dev.touyou.ffmultiplier.models.FNumber
import com.dev.touyou.ffmultiplier.models.FFProblem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    // テスト用のDispatcher
    private val testDispatcher = StandardTestDispatcher()

    // テスト用の問題生成ロジックをバックアップ・リストアするために使用
    private val originalGenerateProblem = FFProblem.generateProblem

    @BeforeTest
    fun setup() {
        // 各テストの前に、問題生成ロジックを固定のものに差し替える
        // これにより、テストの予測可能性が向上する
        // FNumberは0~15の範囲（16進数1桁）または0~225の範囲（16進数2桁）を受け付ける
        FFProblem.generateProblem = createPredictableProblemGenerator()
    }

    @AfterTest
    fun tearDown() {
        // 各テストの後に、元の問題生成ロジックに戻す
        FFProblem.generateProblem = originalGenerateProblem
    }

    // テストヘルパーメソッド
    private fun createPredictableProblemGenerator(): (Int) -> List<FFProblem> = { count ->
        (0 until count).map { i ->
            val firstValue = (i % MAX_HEX_DIGIT_VALUE) // 0~15の範囲でループ
            val secondValue = 1 // 常に1を使用して計算を簡単にする
            FFProblem(FNumber(firstValue), FNumber(secondValue))
        }
    }

    private fun createGameViewModel(): GameViewModel = GameViewModel(testDispatcher)

    private fun inputAnswerToViewModel(viewModel: GameViewModel, answer: String) {
        answer.forEach { viewModel.input(it) }
    }

    companion object {
        private const val MAX_HEX_DIGIT_VALUE = 16
        private const val INITIAL_GAME_TIME = 60
        private const val INITIAL_SCORE = 0
        private const val FAILED_ANSWER_PENALTY = -5
        private const val ONE_SECOND_MILLIS = 1_000L
    }

    @Test
    fun `startGame initializes the game state correctly`() = runTest {
        // Given
        val viewModel = createGameViewModel()
        
        // When
        viewModel.startGame()

        // Then
        assertEquals(INITIAL_GAME_TIME, viewModel.gameTime.value)
        assertEquals(INITIAL_SCORE, viewModel.score.value)
        assertNotNull(viewModel.currentProblem.value)
        assertEquals("", viewModel.userInput.value)
        assertFalse(viewModel.isGameOver.value)
    }

    @Test
    fun `input updates user input correctly`() = runTest {
        // Given
        val viewModel = createGameViewModel()
        
        // When & Then
        viewModel.input('A')
        assertEquals("A", viewModel.userInput.value)
        
        viewModel.input('B')
        assertEquals("AB", viewModel.userInput.value)
        
        viewModel.input('C')
        assertEquals("AB", viewModel.userInput.value) // 2文字を超えて入力されないことを確認
    }

    @Test
    fun `delete removes last character from user input`() = runTest {
        // Given
        val viewModel = createGameViewModel()
        viewModel.input('A')
        viewModel.input('B')
        
        // When & Then
        viewModel.delete()
        assertEquals("A", viewModel.userInput.value)
        
        viewModel.delete()
        assertEquals("", viewModel.userInput.value)
        
        viewModel.delete()
        assertEquals("", viewModel.userInput.value) // 空の入力でクラッシュしないことを確認
    }

    @Test
    fun `submit with correct answer updates score and problem`() = runTest {
        // Given
        val viewModel = createGameViewModel()
        viewModel.startGame()
        val problem = viewModel.currentProblem.value!!
        val correctAnswer = problem.answer.toHex()

        // When
        inputAnswerToViewModel(viewModel, correctAnswer)
        viewModel.submit()

        // Then
        assertTrue(viewModel.score.value > INITIAL_SCORE)
        assertNotEquals(problem, viewModel.currentProblem.value)
        assertEquals("", viewModel.userInput.value)
    }

    @Test
    fun `submit with incorrect answer updates score and problem`() = runTest {
        // Given
        val viewModel = createGameViewModel()
        viewModel.startGame()
        val problem = viewModel.currentProblem.value!!
        val incorrectAnswerValue = (problem.answer.value + 1) % 256
        val incorrectAnswerHex = incorrectAnswerValue.toString(16).padStart(2, '0')

        // When
        inputAnswerToViewModel(viewModel, incorrectAnswerHex)
        viewModel.submit()

        // Then
        assertEquals(FAILED_ANSWER_PENALTY, viewModel.score.value)
        assertNotEquals(problem, viewModel.currentProblem.value)
        assertEquals("", viewModel.userInput.value)
    }

    @Test
    fun `timer finishes game after 60 seconds`() = runTest(testDispatcher) {
        // Given
        val viewModel = createGameViewModel()
        viewModel.startGame()

        // Then - 初期状態の確認
        assertFalse(viewModel.isGameOver.value)
        assertEquals(INITIAL_GAME_TIME, viewModel.gameTime.value)

        // When & Then - 1秒進める
        advanceTimeBy(ONE_SECOND_MILLIS)
        runCurrent()
        assertEquals(59, viewModel.gameTime.value)

        // When & Then - 58秒進める（合計59秒）
        advanceTimeBy(58 * ONE_SECOND_MILLIS)
        runCurrent()
        assertEquals(1, viewModel.gameTime.value)
        assertFalse(viewModel.isGameOver.value, "Game should not be over yet")

        // When & Then - さらに1秒進めて、ちょうど60秒にする
        advanceTimeBy(ONE_SECOND_MILLIS)
        runCurrent()
        assertEquals(0, viewModel.gameTime.value)
        assertTrue(viewModel.isGameOver.value, "Game should be over now")
    }
}
