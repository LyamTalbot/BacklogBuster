package com.lyamtalbot.backlogbuster2.backlogbuster2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert
    suspend fun insert(game: Game)

    @Insert
    suspend fun insertAll(list: List<Game>)

    @Delete
    suspend fun delete(game: Game)

    @Query("DELETE FROM GAME")
    suspend fun deleteGames()

    @Query("DELETE FROM GAME WHERE id = :id")
    suspend fun deleteById(id: Int)
    @Upsert
    suspend fun upsert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Query("SELECT * FROM GAME ORDER BY id")
    suspend fun getAllGames(): List<Game>

    @Query("SElECT * FROM GAME WHERE id = :id")
    fun gameByIdFlow(id: Int): Flow<Game>

    @Query("SELECT * FROM GAME ORDER BY id")
    fun getAllGamesAsFlow(): Flow<List<Game>>

    @Query("SELECT * FROM GAME ORDER BY title")
    fun getAllGamesAsFlowAlpha(): Flow<List<Game>>
}