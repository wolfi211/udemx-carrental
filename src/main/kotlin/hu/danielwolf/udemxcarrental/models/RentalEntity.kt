package hu.danielwolf.udemxcarrental.models

import jakarta.persistence.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "rentals")
data class RentalEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = 0,
    var dateStart: LocalDate,
    var dateEnd: LocalDate,
    var userName: String,
    var userEmail: String,
    var userAddress: String,
    var userPhone: String,
    @ManyToOne var car: CarEntity
) {
    fun getDays(): Long = dateStart.until(dateEnd, ChronoUnit.DAYS) + 1

    fun getFullPrice() = getDays().times(car.dailyPrice)
}
