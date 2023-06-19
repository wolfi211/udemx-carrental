package hu.danielwolf.udemxcarrental.car

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.danielwolf.udemxcarrental.rental.RentalEntity
import jakarta.persistence.*

@Entity
@Table(name = "cars")
data class CarEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var licensePlateNumber: String,
    var dailyPrice: Long,
    @Lob var image: ByteArray? = null,
    @OneToMany @JsonIgnore var rentals: List<RentalEntity>? = null
)