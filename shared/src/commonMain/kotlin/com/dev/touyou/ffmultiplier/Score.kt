package com.dev.touyou.ffmultiplier

import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.Timestamp

data class Score(val user: DocumentReference, val score: Int, val updatedAt: Timestamp)
