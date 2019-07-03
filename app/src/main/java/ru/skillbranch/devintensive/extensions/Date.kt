package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.Plural
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits): Date {
    var time = time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {
        return when (this) {
            SECOND -> Plural("секунду", "секунды", "секунд").getPlural(value.toLong())
            MINUTE -> Plural("минуту", "минуты", "минут").getPlural(value.toLong())
            HOUR -> Plural("час", "часа", "часов").getPlural(value.toLong())
            DAY -> Plural("день", "дня", "дней").getPlural(value.toLong())
        }
    }
}