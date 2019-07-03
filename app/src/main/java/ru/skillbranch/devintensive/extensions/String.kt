package ru.skillbranch.devintensive.extensions


fun String.truncate(value: Int = 16): String {
//    val trimmed = this.trim()
//    return if (trimmed.length <= value) {
//        trimmed
//    } else {
//        trimmed.slice(0 until value).trim().plus("...")
//    }
    val trimmedMes = this.trim()
    val subLine = trimmedMes.substring(0, value + 1).trim()
    return if (subLine.length < trimmedMes.length) "$subLine..." else subLine
}

/**
 *  & < > ' "
 */
fun String.stripHtml(): String {
    return  this.replace(Regex("<.*?>"), "")
        .replace(Regex("&.*?;"), "")
        .replace(Regex("\\s+"), " ")
}