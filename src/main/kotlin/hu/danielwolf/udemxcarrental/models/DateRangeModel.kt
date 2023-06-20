package hu.danielwolf.udemxcarrental.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class DateRangeModel (
    var start: LocalDate? = null,
    var end: LocalDate? = null
)
