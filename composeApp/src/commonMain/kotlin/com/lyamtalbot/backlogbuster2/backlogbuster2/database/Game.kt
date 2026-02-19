package com.lyamtalbot.backlogbuster2.backlogbuster2.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant


val dateFormat = LocalDateTime.Format {
    day();char(' '); monthName(MonthNames.ENGLISH_FULL); char(' '); year()
}

val ratingsMap: Map<Int, String> = mapOf(
    1 to "Awful",
    2 to "Bad",
    3 to "Below Average",
    4 to "Above Average",
    5 to "Good",
    6 to "Great",
)

@Entity
@Serializable
data class Game (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rating: Int = -1,
    val timeToBeat: Int = -1,
    val title: String = "",
    val platform: String = "",
    val genre : String = "",
    val completed: Boolean = false,
    val favourite : Boolean = false,
    val playingNow: Boolean = false,
    val dateCreated: Instant = Clock.System.now(),
    val dateCompleted: Instant? = null
) {
    fun getRatingString(): String {
        if (rating == -1) return "--"
        return rating.toString()
    }

    fun getPlatformString(): String {
        if (platform == "") return "--"
        return platform
    }

    fun getGenreString(): String {
        if (genre.isEmpty()) return "--"
        return genre
    }

    fun getTimeToBeatString(): String {
        if (timeToBeat == -1) return "--"
        return timeToBeat.toString()
    }

    fun getCreatedTimeString(): String {
        return dateCreated.toLocalDateTime(TimeZone.currentSystemDefault()).format(dateFormat)
    }

    fun getFinishTimeString(): String {
        if (dateCompleted == null) return "N/A"
        return dateCompleted.toLocalDateTime(TimeZone.currentSystemDefault()).format(dateFormat)
    }
}
@ProvidedTypeConverter
class Converters{
    @TypeConverter
    fun fromEpochMilliseconds(value: Long?): Instant? {
        return value?.let {Instant.fromEpochMilliseconds(it)}
    }

    @TypeConverter
    fun toEpochMilliseconds(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
}

fun boolToString(bool: Boolean) = if (bool) "Yes" else "No"