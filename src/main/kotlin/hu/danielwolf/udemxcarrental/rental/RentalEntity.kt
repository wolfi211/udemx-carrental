package hu.danielwolf.udemxcarrental.rental

import hu.danielwolf.udemxcarrental.car.CarEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "rentals")
data class RentalEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    var dateStart: LocalDate,
    var dateEnd: LocalDate,
    var userName: String,
    var userEmail: String,
    var userAddress: String,
    var userPhone: String,
    @ManyToOne var car: CarEntity
)
