package hu.danielwolf.udemxcarrental.repositories

import hu.danielwolf.udemxcarrental.models.CarEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarRepository : JpaRepository<CarEntity, Long>