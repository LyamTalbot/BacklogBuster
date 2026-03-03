package com.lyamtalbot.backlogbuster2.backlogbuster2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

@Dao
interface GameDao {
    @Insert
    suspend fun insert(game: Game)

    @Insert
    suspend fun insertAll(list: List<Game>)

    @Delete
    suspend fun delete(game: Game)

    @Query("DELETE FROM GAME")
    suspend fun deleteAllGames()

    @Query("DELETE FROM GAME where id IN (:gameIDs)")
    suspend fun deleteGames(gameIDs: Set<Int>)

    @Query("DELETE FROM GAME WHERE id = :id")
    suspend fun deleteById(id: Int)
    @Upsert
    suspend fun upsert(game: Game)


    @Update
    suspend fun update(game: Game)

    @Query("SELECT * FROM GAME WHERE id = :id")
    suspend fun getGameById(id: Int): Game?

    @Query("SELECT * FROM GAME ORDER BY id")
    suspend fun getAllGames(): List<Game>

    @Query("SElECT * FROM GAME WHERE id = :id")
    fun gameByIdFlow(id: Int): Flow<Game?>

    @Query("UPDATE GAME set completed = :completed, dateCompleted = :completionTime WHERE id = :id")
    suspend fun updateCompleted(id: Int, completed: Boolean, completionTime: Instant?)

    @Query("SELECT * FROM GAME ORDER BY id")
    fun getAllGamesAsFlow(): Flow<List<Game>>

    @Query("SELECT * FROM GAME ORDER BY title")
    fun getAllGamesAsFlowAlpha(): Flow<List<Game>>
}