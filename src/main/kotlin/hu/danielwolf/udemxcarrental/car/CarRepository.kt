package hu.danielwolf.udemxcarrental.car

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarRepository : JpaRepository<CarEntity, Long>