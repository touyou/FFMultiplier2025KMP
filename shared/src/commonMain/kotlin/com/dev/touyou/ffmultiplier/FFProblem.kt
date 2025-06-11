package com.dev.touyou.ffmultiplier

/**
 * 問題のデータクラス
 */
data class FFProblem(val a: FNumber, val b: FNumber) {
    /** 問題の答え */
    val answer: FNumber
        get() = a * b

    companion object {
        /** 問題リストの生成 */
        fun generateProblem(count: Int): List<FFProblem> {
            val problems = mutableSetOf<FFProblem>()
            while (problems.size < count) {
                problems.add(FFProblem(FNumber.random(), FNumber.random()))
            }
            return problems.shuffled()
        }
    }
}