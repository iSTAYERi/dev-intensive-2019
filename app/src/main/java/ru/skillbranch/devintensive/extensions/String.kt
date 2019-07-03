package ru.skillbranch.devintensive.extensions


fun String.truncate(value: Int = 16): String {
    return if (this.length <= value) {
        this.trim()
    } else {
        this.slice(0 until value).trim().plus("...")
    }

}

/**
 *  & < > ' "
 */
fun String.stripHtml(): String {
    return  this.replace(Regex("<.*?>"), "")
        .replace(Regex("&.*?;"), "")
        .replace(Regex("\\s+"), " ")
}