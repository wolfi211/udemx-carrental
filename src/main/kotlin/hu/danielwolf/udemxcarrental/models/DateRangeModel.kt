package hu.danielwolf.udemxcarrental.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class DateRangeModel (
    var start: LocalDate? = null,
    var end: LocalDate? = null
) {
    fun getFormattedStartDate(): String? = start?.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))

    fun getFormattedEndDate(): String? = end?.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
}
