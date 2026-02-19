package com.lyamtalbot.backlogbuster2.backlogbuster2.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


fun getGameDatabase(builder: RoomDatabase.Builder<GameDatabase>): GameDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addTypeConverter(Converters())
        .build()
}