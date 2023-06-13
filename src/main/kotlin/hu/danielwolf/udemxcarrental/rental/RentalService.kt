package hu.danielwolf.udemxcarrental.rental

import hu.danielwolf.udemxcarrental.SearchRentalForm
import hu.danielwolf.udemxcarrental.car.CarEntity
import hu.danielwolf.udemxcarrental.car.CarService
import org.springframework.stereotype.Service

@Service
class RentalService(val rentalRepository: RentalRepository, val carService: CarService) {

    fun getCarsByDate(searchRentalForm: SearchRentalForm): List<CarEntity> {
        val rentals = rentalRepository.findAll()
        val validCars = mutableListOf<CarEntity>()
        for(rental : RentalEntity in rentals) {
            if(!isRentedAtDate(rental, searchRentalForm)) {
                validCars.add(rental.car)
            }
        }
        return validCars
    }

    fun isRentedAtDate(rental: RentalEntity, searchRentalForm: SearchRentalForm): Boolean =
        (rental.dateStart < searchRentalForm.dateStart && rental.dateEnd > searchRentalForm.dateEnd) ||
        (rental.dateStart > searchRentalForm.dateStart && rental.dateEnd < searchRentalForm.dateEnd) ||
        (rental.dateStart < searchRentalForm.dateStart && rental.dateEnd > searchRentalForm.dateStart) ||
        (rental.dateStart < searchRentalForm.dateEnd && rental.dateEnd > searchRentalForm.dateEnd)

}