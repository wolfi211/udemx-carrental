package hu.danielwolf.udemxcarrental.services

import hu.danielwolf.udemxcarrental.models.DateRangeModel
import hu.danielwolf.udemxcarrental.models.CarEntity
import hu.danielwolf.udemxcarrental.models.RentalEntity
import hu.danielwolf.udemxcarrental.repositories.RentalRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@Service
class RentalService(val repository: RentalRepository, val carService: CarService) {

    fun create(rental: RentalEntity): RentalEntity = repository.save(rental)

    fun getAll(): List<RentalEntity> = repository.findAll()

    fun getById(id: Long): RentalEntity = repository.findByIdOrNull(id) ?:
        throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getCarsByDate(dateRange: DateRangeModel): List<CarEntity> {
        val rentals = repository.findAll()
        val validCars = carService.getAllActive().toMutableList()
        for(rental : RentalEntity in rentals) {
            if(isRentedAtDate(rental, dateRange)) {
                validCars.remove(rental.car)
            }
        }
        return validCars
    }

    fun update(id: Long, rental: RentalEntity): RentalEntity {
        return if (repository.existsById(id)) {
            rental.id = id
            repository.save(rental)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun delete(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun setCarActive(id: Long, active: Boolean): CarEntity {
        val car = carService.getById(id)
        val rentals = repository.findByCar(car)
        val currentDate = LocalDate.now()
        for(rental : RentalEntity in rentals) {
            if(currentDate <= rental.dateEnd) {
                throw ResponseStatusException(HttpStatus.NOT_ACCEPTABLE)
            }
        }
        return carService.setActive(id, active)
    }

    fun isRentedAtDate(rental: RentalEntity, dateRange: DateRangeModel): Boolean =
        (rental.dateStart <= dateRange.start && rental.dateEnd >= dateRange.end) ||
                (rental.dateStart >= dateRange.start && rental.dateEnd <= dateRange.end) ||
                (rental.dateStart <= dateRange.start && rental.dateEnd >= dateRange.start) ||
                (rental.dateStart <= dateRange.end && rental.dateEnd >= dateRange.end)
}