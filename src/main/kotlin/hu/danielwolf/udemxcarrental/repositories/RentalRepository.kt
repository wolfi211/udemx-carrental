package hu.danielwolf.udemxcarrental.repositories

import hu.danielwolf.udemxcarrental.models.RentalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentalRepository : JpaRepository<RentalEntity, Long>