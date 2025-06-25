package com.dev.touyou.ffmultiplier

import com.dev.touyou.ffmultiplier.models.Score
import com.dev.touyou.ffmultiplier.models.User

/**
 * Interface for the Firestore data source.
 * This interface defines the contract for interacting with Firestore.
 */
interface FirestoreDataSourceContract {
    fun subscribeToScoreCollection(
        onUpdate: (List<Score>) -> Unit,
        onError: (Throwable) -> Unit
    ): Subscription

    fun fetchUser(
        docsPath: String,
        onResult: (User?) -> Unit,
        onError: (Throwable) -> Unit
    ): Unit
}

interface Subscription {
    fun unsubscribe()
}