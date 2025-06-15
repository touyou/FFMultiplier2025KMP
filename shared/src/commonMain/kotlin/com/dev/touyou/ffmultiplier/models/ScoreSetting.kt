package com.dev.touyou.ffmultiplier.models

import kotlin.math.min

/**
 * スコア算出の定数
 */
object ScoreSetting {
    /** 正解時のスコア */
    const val acceptedPoints: Int = 10

    /** 不正解時のスコア */
    const val failedPoints: Int = -5

    /** コンボボーナス */
    const val comboBonus: Int = 5

    /** コンボボーナスの最大値 */
    const val maxComboBonus: Int = 15

    /**
     * ボーナスを計算する
     */
    fun calculateBonus(combo: Int): Int {
        val bonus = (combo / 5) * comboBonus
        return min(bonus, maxComboBonus)
    }
}