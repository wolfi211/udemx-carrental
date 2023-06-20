package hu.danielwolf.udemxcarrental.services

import hu.danielwolf.udemxcarrental.models.DateRangeModel
import hu.danielwolf.udemxcarrental.models.CarEntity
import hu.danielwolf.udemxcarrental.models.RentalEntity
import hu.danielwolf.udemxcarrental.repositories.RentalRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class RentalService(val repository: RentalRepository, val carService: CarService) {

    fun create(rental: RentalEntity): RentalEntity = repository.save(rental)

    fun getAll(): List<RentalEntity> = repository.findAll()

    fun getById(id: Long): RentalEntity = repository.findByIdOrNull(id) ?:
        throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getCarsByDate(searchRentalForm: DateRangeModel): List<CarEntity> {
        val rentals = repository.findAll()
        val validCars = carService.getAll().toMutableList()
        for(rental : RentalEntity in rentals) {
            if(isRentedAtDate(rental, searchRentalForm)) {
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

    fun isRentedAtDate(rental: RentalEntity, searchRentalForm: DateRangeModel): Boolean =
        (rental.dateStart < searchRentalForm.start && rental.dateEnd > searchRentalForm.end) ||
                (rental.dateStart > searchRentalForm.start && rental.dateEnd < searchRentalForm.end) ||
                (rental.dateStart < searchRentalForm.start && rental.dateEnd > searchRentalForm.start) ||
                (rental.dateStart < searchRentalForm.end && rental.dateEnd > searchRentalForm.end)
}