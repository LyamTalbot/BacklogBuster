package com.lyamtalbot.backlogbuster2.backlogbuster2.database

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
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

val favouriteBackground = Color(26,137,20,214)

val ratingsMap: Map<Int, String> = mapOf(
    1 to "Awful",
    2 to "Bad",
    3 to "Below Average",
    4 to "Above Average",
    5 to "Good",
    6 to "Great",
)

val colourMap: Map<Int, Color> = mapOf(
    1 to Color(255, 0, 0, 229),
    2 to Color(225, 90, 0, 229),
    3 to Color(255, 170, 0, 229),
    4 to Color(200, 255, 0, 229),
    5 to Color(150, 255, 0, 229),
    6 to Color(0, 255, 0, 229),
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
        return "$timeToBeat hours"
    }

    fun timeToBeatTextField(): String {
        if (timeToBeat == -1) return "--"
        else return timeToBeat.toString()
    }

    fun getRatingTextField(): String {
        if (rating == -1) return "--"
        else return "${ratingsMap[this.rating]}: ${this.rating}"
    }

    fun getCreatedTimeString(): String {
        return dateCreated.toLocalDateTime(TimeZone.currentSystemDefault()).format(dateFormat)
    }

    fun getFinishTimeString(): String {
        if (dateCompleted == null) return "N/A"
        return dateCompleted.toLocalDateTime(TimeZone.currentSystemDefault()).format(dateFormat)
    }

    fun getTimeTakenString(): String {
        if (timeToBeat == -1) return "--"
        return timeToBeat.toString()
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