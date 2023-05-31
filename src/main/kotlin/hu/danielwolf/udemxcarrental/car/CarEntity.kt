package hu.danielwolf.udemxcarrental.car

import jakarta.persistence.*

@Entity
data class CarEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    val licensePlateNumber: String,
    val price: Long
)