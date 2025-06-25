package com.dev.touyou.ffmultiplier.models

/**
 * 問題のデータクラス
 */
data class FFProblem(val val1: FNumber, val val2: FNumber) {
    /** 問題の答え */
    val answer: FNumber
        get() = val1 * val2

    companion object {
        /** 問題リストの生成 */
        var generateProblem: (Int) -> List<FFProblem> = {
            val problems = mutableListOf<FFProblem>()
            while (problems.size < it) {
                problems.add(FFProblem(FNumber.random(), FNumber.random()))
            }
            problems.shuffled()
        }
    }
}