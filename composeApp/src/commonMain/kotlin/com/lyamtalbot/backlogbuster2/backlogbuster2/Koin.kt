package com.lyamtalbot.backlogbuster2.backlogbuster2

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration


fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin{
        appDeclaration()
        modules(
            commonModule() + platformModule()
        )
    }

}

fun initKoinIos() = initKoin(appDeclaration = {})