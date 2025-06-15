package com.dev.touyou.ffmultiplier.viewModels

import androidx.lifecycle.ViewModel
import com.dev.touyou.ffmultiplier.FirestoreDataSourceContract
import com.dev.touyou.ffmultiplier.Subscription
import com.dev.touyou.ffmultiplier.models.Score
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * ViewModel for managing and displaying the ranking of scores.
 *
 * This ViewModel is responsible for fetching and observing score data from Firestore
 * and providing it to the UI. It uses Koin for dependency injection to get an
 * instance of `FirestoreDataSourceContract`.
 *
 * The scores are exposed as a `StateFlow` which allows the UI to reactively update
 * when the scores change.
 *
 * It provides methods to start and stop observing scores from Firestore, allowing
 * the ViewModel to manage the lifecycle of the Firestore subscription.
 */
class RankingViewModel: ViewModel(), KoinComponent {
    private val firestoreDataSource: FirestoreDataSourceContract by inject()
    private val _scores = MutableStateFlow<List<Score>>(emptyList())
    val scores: StateFlow<List<Score>> = _scores

    private var subscription: Subscription? = null

    /**
     * Starts observing the scores collection in Firestore.
     *
     * This function subscribes to the score collection using the `firestoreDataSource`.
     * When the scores are updated, the `_scores` LiveData is updated with the new scores.
     * If an error occurs during the subscription, it will be handled by the `onError` callback.
     */
    fun startObservingScores() {
        subscription = firestoreDataSource.subscribeToScoreCollection(
            onUpdate = { scores ->
                _scores.value = scores
            },
            onError = { error ->
                // Handle the error
            }
        )
    }

    /**
     * Stops observing scores from Firestore.
     *
     * This function unsubscribes from the Firestore score subscription, if one exists,
     * and sets the subscription to null to prevent further updates.
     */
    fun stopObservingScores() {
        subscription?.unsubscribe()
        subscription = null
    }
}