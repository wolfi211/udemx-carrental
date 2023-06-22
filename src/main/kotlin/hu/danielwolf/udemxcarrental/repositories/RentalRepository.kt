package hu.danielwolf.udemxcarrental.repositories

import hu.danielwolf.udemxcarrental.models.CarEntity
import hu.danielwolf.udemxcarrental.models.RentalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface RentalRepository : JpaRepository<RentalEntity, Long> {
    @Transactional
    fun findByCar(car: CarEntity): List<RentalEntity>
}