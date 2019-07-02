package ru.skillbranch.devintensive.extentions

import ru.skillbranch.devintensive.models.Plural
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils
import java.util.*


fun User.toUserView(): UserView {

    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status =
        when {
            lastVisit == null -> "Еще ни разу не был"
            isOnline -> "online"
            else -> "Последний раз был ${lastVisit!!.humanizeDiff()}"
        }

    return UserView(
        id,
        "$firstName $lastName",
        nickName,
        avatar,
        status,
        initials
    )
}

fun Date.humanizeDiff(date: Date = Date()): String {

    val time = this.time
    val currTime = date.time
    val diff = time - currTime
    val pluralMinutes = Plural("минуту", "минуты", "минут")
    val pluralHours = Plural("час", "часа", "часов")
    val pluralDays = Plural("день", "дня", "дней")

    /**
     * 45м - 2700
     * 75м - 4500
     * 22ч - 79_200
     * 26ч - 93_600
     * 360д - 31_104_000L
     */
    return when(diff) {
        in -45_000 until -1000 -> "несколько секунд назад"
        in 1000 until 45_000 -> "через несколько секунд"

        in -75_000 until -45_000 -> "минуту назад"
        in 45_000 until 75_000 -> "через минуту"

        in -2_700_000 until -75_000 -> "${pluralMinutes.getPlural(-diff / 60_000)} назад"
        in 75_000 until 2_700_000 -> "через ${pluralMinutes.getPlural(diff / 60_000)}"

        in -4_500_000 until -2700_000 -> "час назад"
        in 2700_000 until 4_500_000 -> "через час"

        in -79_200_000 until -4500_000 -> "${pluralHours.getPlural(-diff / 3_600_000)} назад"
        in 4_500_000 until 79_200_000 -> "через ${pluralHours.getPlural(diff / 3_600_000)}"

        in -93_600_000 until -79_200_000 -> "день назад"
        in 79_200_000 until 93_600_000 -> "через день"

        in -31_104_000_000L until -93_600_000 -> "${pluralDays.getPlural(-diff / (3_600_000 * 24))} назад"
        in 93_600_000 until 31_104_000_000L -> "через ${pluralDays.getPlural(diff / (3_600_000 * 24))}"

        in Long.MIN_VALUE until -31_104_000_000L -> "более года назад"
        in 31_104_000_000L until Long.MAX_VALUE -> "более чем через год"

        else -> return "только что"
    }
}