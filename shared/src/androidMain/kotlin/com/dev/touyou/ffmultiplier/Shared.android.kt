package com.dev.touyou.ffmultiplier

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    single {
        val firestore = Firebase.firestore
        FirestoreDataSource(firestore)
    } bind FirestoreDataSourceContract::class
}