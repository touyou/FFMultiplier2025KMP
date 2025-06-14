package com.dev.touyou.ffmultiplier

interface SwiftLibDependencyFactoryContract {
    fun provideFirestoreRepository(): FirestoreRepository
}