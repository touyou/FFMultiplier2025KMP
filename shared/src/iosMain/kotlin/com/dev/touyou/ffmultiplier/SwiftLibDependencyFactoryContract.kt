package com.dev.touyou.ffmultiplier

interface SwiftLibDependencyFactoryContract {
    fun provideFirestoreDataSource(): FirestoreDataSourceContract
}