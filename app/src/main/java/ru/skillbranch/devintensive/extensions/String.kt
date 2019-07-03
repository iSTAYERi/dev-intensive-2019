package ru.skillbranch.devintensive.extensions


fun String.truncate(value: Int = 16): String {
    return this.slice(0 until value).trim().plus("...")
}

/**
 *  & < > ' "
 */
fun String.stripHtml(): String {
    return  this.replace(Regex("<.*?>"), "")
        .replace(Regex("&.*?;"), "")
        .replace(Regex("\\s+"), " ")
}