package hu.danielwolf.udemxcarrental.repositories

import hu.danielwolf.udemxcarrental.models.CarEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface CarRepository : JpaRepository<CarEntity, Long> {
    @Transactional
    fun findByActiveEquals(active: Boolean): List<CarEntity>
}