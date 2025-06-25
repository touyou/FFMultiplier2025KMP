package com.dev.touyou.ffmultiplier

import com.dev.touyou.ffmultiplier.models.Score
import com.dev.touyou.ffmultiplier.models.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDataSource(
    private val firestore: FirebaseFirestore
): FirestoreDataSourceContract {
    override fun subscribeToScoreCollection(
        onUpdate: (List<Score>) -> Unit,
        onError: (Throwable) -> Unit
    ): Subscription {
        val listener = firestore.collection("scores").addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }

            val documents = value?.documents ?: run {
                onUpdate(emptyList())
                return@addSnapshotListener
            }
            val scores = documents.mapNotNull { document ->
                val data = document.data ?: return@mapNotNull null
                Score(
                    userRef = (data["user"] as DocumentReference).path,
                    score = (data["score"] as Number).toInt(),
                    updatedAt = (data["updatedAt"] as Timestamp).toDate().time
                )
            }
            val sortedScores = scores.sortedByDescending { it.score }
            onUpdate(sortedScores)
        }

        return object: Subscription {
            override fun unsubscribe() {
                listener.remove()
            }
        }
    }

    override fun fetchUser(
        docsPath: String,
        onResult: (User?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        firestore.document(docsPath).get().addOnSuccessListener {
            val data = it.data ?: run {
                onResult(null)
                return@addOnSuccessListener
            }
            val user = User(
                name = data["name"] as String,
                uuid = data["uuid"] as String
            )
            onResult(user)
        }.addOnFailureListener {
            onError(it)
        }
    }
}