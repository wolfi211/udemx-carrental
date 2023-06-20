package hu.danielwolf.udemxcarrental.models

import java.time.LocalDate

data class RentalModel(
    var dateStart: LocalDate? = null,
    var dateEnd: LocalDate? = null,
    var userName: String? = null,
    var userEmail: String? = null,
    var userAddress: String? = null,
    var userPhone: String? = null,
    var carId: Long? = null
)
