/// Converts a Firestore `Timestamp` object to a millisecond-based Unix epoch time (`Int64`).
/// - Parameter timestamp: The Firestore `Timestamp` instance to convert.
/// - Returns: The timestamp as the number of milliseconds since January 1, 1970 (Unix epoch).
//
//  FirebaseCollectionSubscriber.swift
//  iosApp
//
//  Created by 藤井陽介 on 2025/06/13.
//

import Foundation
import FirebaseFirestore
import Shared

class FirestoreDataSource: Shared.FirestoreDataSourceContract {
  func subscribeToScoreCollection(
    onUpdate: @escaping ([Score]) -> Void,
    onError: @escaping (KotlinThrowable) -> Void
  ) -> any Subscription {
    let db = Firestore.firestore()
    let listener = db.collection("scores").addSnapshotListener { querySnapshot, error in
      if let error = error {
        onError(KotlinThrowable(message: error.localizedDescription))
        return
      }
      
      guard let documents = querySnapshot?.documents else {
        onUpdate([])
        return
      }
      let scores = documents.map { $0.data() }.map { data in
        Score(
          userRef: (data["user"] as! DocumentReference).path,
          score: data["score"] as! Int32,
          updatedAt: timestampToMillis(data["updatedAt"] as! Timestamp)
        )
      }
      onUpdate(scores)
    }
    
    return ScoreCollectionSubscription(listener: listener)
  }
}

class ScoreCollectionSubscription: Subscription {
  let listener: ListenerRegistration?
  
  init(listener: ListenerRegistration?) {
    self.listener = listener
  }
  
  func unsubscribe() {
    listener?.remove()
  }
}

/// Converts a Firestore `Timestamp` to the number of milliseconds since the Unix epoch (January 1, 1970).
/// - Parameter timestamp: The Firestore `Timestamp` instance to convert.
/// - Returns: The number of milliseconds since the Unix epoch as an `Int64`.
func timestampToMillis(_ timestamp: Timestamp) -> Int64 {
  return Int64(timestamp.dateValue().timeIntervalSince1970 * 1000)
}
