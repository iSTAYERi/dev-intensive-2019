package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = fullName?.split(" ")
        val firstName: String?
        val lastName: String?

        if (fullName.isNullOrBlank()) {
            firstName = null
            lastName = null
        } else {
            firstName = parts?.getOrNull(0)
            lastName = parts?.getOrNull(1)
        }

        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}