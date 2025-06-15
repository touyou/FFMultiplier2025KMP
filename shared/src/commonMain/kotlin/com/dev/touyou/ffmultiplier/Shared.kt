package com.dev.touyou.ffmultiplier

import com.dev.touyou.ffmultiplier.viewModels.RankingViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val useCaseModule = module {}

val repositoryModule = module {}

val viewModelModule = module {
    viewModelOf(::RankingViewModel)
}

expect val platformModule: Module

fun initAndroidKoin(appDeclaration:KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        platformModule
    )
}

fun initIosKoin(onKoinStart: () -> Module) {
    startKoin {
        modules(
            viewModelModule,
            useCaseModule,
            repositoryModule,
            onKoinStart(),
        )
    }
}