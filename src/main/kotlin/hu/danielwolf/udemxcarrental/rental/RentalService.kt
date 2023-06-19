package hu.danielwolf.udemxcarrental.rental

import hu.danielwolf.udemxcarrental.models.SearchModel
import hu.danielwolf.udemxcarrental.car.CarEntity
import hu.danielwolf.udemxcarrental.car.CarService
import org.springframework.stereotype.Service

@Service
class RentalService(val rentalRepository: RentalRepository, val carService: CarService) {

    fun getCarsByDate(searchRentalForm: SearchModel): List<CarEntity> {
        val rentals = rentalRepository.findAll()
        val validCars = mutableListOf<CarEntity>()
        for(rental : RentalEntity in rentals) {
            if(!isRentedAtDate(rental, searchRentalForm)) {
                validCars.add(rental.car)
            }
        }
        return validCars
    }

    fun isRentedAtDate(rental: RentalEntity, searchRentalForm: SearchModel): Boolean =
        (rental.dateStart < searchRentalForm.start && rental.dateEnd > searchRentalForm.end) ||
        (rental.dateStart > searchRentalForm.start && rental.dateEnd < searchRentalForm.end) ||
        (rental.dateStart < searchRentalForm.start && rental.dateEnd > searchRentalForm.start) ||
        (rental.dateStart < searchRentalForm.end && rental.dateEnd > searchRentalForm.end)

}