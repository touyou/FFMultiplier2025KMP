package com.dev.touyou.ffmultiplier

/**
 * FirestoreのRepositoryのインターフェース
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