package hu.danielwolf.udemxcarrental.carbrand

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.danielwolf.udemxcarrental.carmodel.CarModelEntity
import jakarta.persistence.*

@Entity
@Table(name = "brands")
data class CarBrandEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    val name: String,
    @OneToMany @JsonIgnore val carModels: List<CarModelEntity>
)