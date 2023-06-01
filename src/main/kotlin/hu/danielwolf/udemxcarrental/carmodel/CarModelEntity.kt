package hu.danielwolf.udemxcarrental.carmodel

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.danielwolf.udemxcarrental.car.CarEntity
import hu.danielwolf.udemxcarrental.carbrand.CarBrandEntity
import jakarta.persistence.*

@Entity
@Table(name = "models")
data class CarModelEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    val name: String,
    @ManyToOne val carBrand: CarBrandEntity,
    @OneToMany @JsonIgnore val cars: List<CarEntity>
)
