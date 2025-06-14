package com.dev.touyou.ffmultiplier

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val userRef: String,
    val score: Int,
    val updatedAt: Long
)
