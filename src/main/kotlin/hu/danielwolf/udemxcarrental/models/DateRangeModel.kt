package hu.danielwolf.udemxcarrental.models

import java.time.LocalDate
import java.time.temporal.ChronoUnit


data class DateRangeModel (
    var start: LocalDate? = null,
    var end: LocalDate? = null
) {
    fun getDays(): Long? = start?.until(end, ChronoUnit.DAYS)!! + 1
}
