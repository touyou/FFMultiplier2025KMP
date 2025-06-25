package com.dev.touyou.ffmultiplier.models

/**
 * 16進数を表すデータクラス
 */
data class FNumber(private var _value: Int): Comparable<FNumber> {
    init {
        require(_value >= 0) { "FNumber value must be non-negative" }
        require(_value <= 15 * 15) { "FNumber value must not exceed 15*15" }
    }

    /** 1の位 */
    val value0: Int
        get() = _value % 16

    /** 16の位 */
    val value1: Int
        get() = _value / 16

    operator fun times(other: FNumber): FNumber {
        return FNumber(this._value * other._value)
    }

    operator fun timesAssign(other: FNumber) {
        this._value *= other._value
    }

    override fun compareTo(other: FNumber): Int {
        return this._value.compareTo(other._value)
    }

    companion object {
        /** 乱数を生成する */
        fun random(): FNumber {
            return FNumber((0..15).random())
        }

        /** 16進数の文字列からFNumberを生成する */
        fun fromHex(hex: String): FNumber {
            return FNumber(hex.toInt(16))
        }
    }
}

