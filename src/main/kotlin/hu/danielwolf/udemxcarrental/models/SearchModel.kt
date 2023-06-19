package hu.danielwolf.udemxcarrental.models

import java.time.LocalDate


data class SearchModel (
    var start: LocalDate? = null,
    var end: LocalDate? = null
)
