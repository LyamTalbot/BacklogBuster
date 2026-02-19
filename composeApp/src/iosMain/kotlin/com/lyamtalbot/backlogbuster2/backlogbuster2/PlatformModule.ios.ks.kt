package com.lyamtalbot.backlogbuster2.backlogbuster2

import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDatabase
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.getDatabaseBuilder
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.getGameDatabase
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformModule(): Module = module {
    single<GameDatabase> {
        val builder = getDatabaseBuilder()
        getGameDatabase(builder)
    }
}