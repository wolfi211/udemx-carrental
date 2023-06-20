package hu.danielwolf.udemxcarrental.controllers

import hu.danielwolf.udemxcarrental.models.RentalEntity
import hu.danielwolf.udemxcarrental.models.RentalModel
import hu.danielwolf.udemxcarrental.services.CarService
import hu.danielwolf.udemxcarrental.models.DateRangeModel
import hu.danielwolf.udemxcarrental.services.RentalService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate


@Controller
class RentalController (val carService: CarService, val rentalService: RentalService) {

    @GetMapping("/search")
    fun searchRentalForm(model: Model): String {
        model["title"] = "Car Rental"
        model["search"] = DateRangeModel()
        return "searchrentalform"
    }

    @PostMapping("/search")
    fun searchRentalResult(@ModelAttribute search: DateRangeModel, model: Model): String? {
        val cars = rentalService.getCarsByDate(search)
        model["title"] = "Car Rental"
        model["cars"] = cars
        model["daterange"] = search
        return "searchrentalresult"
    }

    @PostMapping("/rentals/new")
    fun newRentalForm(
        model: Model,
        @RequestParam("carid") carid: Long,
        @RequestParam("startDate") startDate: LocalDate,
        @RequestParam("endDate") endDate: LocalDate
    ): String {
        model["title"] = "Car Rental"
        model["rental"] = RentalModel()
        model["car"] = carService.getById(carid)
        model["daterange"] = DateRangeModel(start = startDate, end = endDate)
        return "newrentalform"
    }

    @PostMapping("/rentals")
    fun newRental(
        model: Model,
        @ModelAttribute rental: RentalModel
    ): String {
        val newRental = RentalEntity(
            dateStart = rental.dateStart!!,
            dateEnd = rental.dateEnd!!,
            userName = rental.userName!!,
            userEmail = rental.userEmail!!,
            userAddress = rental.userAddress!!,
            userPhone = rental.userPhone!!,
            car = carService.getById(rental.carId!!)
        )
        rentalService.create(newRental)
        return "success"
    }
}