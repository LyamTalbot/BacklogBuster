package com.lyamtalbot.backlogbuster2.backlogbuster2.database

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters


@Database(
    entities = [Game::class],
    version = 1
    )
@ConstructedBy(GameDatabaseConstructor::class)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun getGameDao(): GameDao
}
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object GameDatabaseConstructor: RoomDatabaseConstructor<GameDatabase> {
    override fun initialize(): GameDatabase
}
