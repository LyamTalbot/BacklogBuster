package com.lyamtalbot.backlogbuster2.backlogbuster2.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<GameDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("game_backlog.db")

    return Room.databaseBuilder<GameDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}