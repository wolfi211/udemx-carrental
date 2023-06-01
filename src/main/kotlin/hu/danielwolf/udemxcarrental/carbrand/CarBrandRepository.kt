package hu.danielwolf.udemxcarrental.carbrand

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarBrandRepository : JpaRepository<CarBrandEntity, Long>