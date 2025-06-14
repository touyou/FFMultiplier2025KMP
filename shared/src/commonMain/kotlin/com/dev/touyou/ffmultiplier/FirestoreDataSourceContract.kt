package com.dev.touyou.ffmultiplier

/**
 * Interface for the Firestore data source.
 * This interface defines the contract for interacting with Firestore.
 */
interface FirestoreDataSourceContract {
    fun subscribeToScoreCollection(
        onUpdate: (List<Score>) -> Unit,
        onError: (Throwable) -> Unit
    ): Subscription
}

interface  Subscription {
    fun unsubscribe()
}