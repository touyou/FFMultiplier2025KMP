package com.dev.touyou.ffmultiplier

import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {}

fun createSwiftLibDependencyModule(factory: SwiftLibDependencyFactoryContract): Module = module {
    single { factory.provideFirestoreRepository() } bind FirestoreRepository::class
}