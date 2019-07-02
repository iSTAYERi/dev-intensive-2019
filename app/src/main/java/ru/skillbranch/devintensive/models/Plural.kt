package ru.skillbranch.devintensive.models


/**
 *  The Plural class can return plural String in a relation to number value.
 * @param one один. Пример: одна минута
 * @param few несколько. Пример: три минуты
 * @param many много. Пример: семь минут
 */
class Plural(
    private val one: String,
    private val few: String,
    private val many: String
) {

    fun getPlural(value: Long): String {
        val sValue = value.toString()
        val length = sValue.length
        if (length >= 2) {
            when (sValue.slice((length - 2) until length)) {
                in "11".."14" -> return "$sValue $many"
            }
        }
        return when (sValue[length - 1]) {
            '0' -> "$sValue $many"
            '1' -> "$sValue $one"
            in '2'..'4' -> "$sValue $few"
            else -> "$sValue $many"
        }
    }

}