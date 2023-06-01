package hu.danielwolf.udemxcarrental.rental

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RentalRepository : JpaRepository<RentalEntity, Long>