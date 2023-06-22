package hu.danielwolf.udemxcarrental.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "cars")
data class CarEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var licensePlateNumber: String,
    var dailyPrice: Long,
    var active: Boolean? = true,
    @Lob var image: ByteArray? = null,
    @OneToMany @JsonIgnore var rentals: List<RentalEntity>? = null
) {
    fun getPriceForDays(days: Long) = days * dailyPrice
}