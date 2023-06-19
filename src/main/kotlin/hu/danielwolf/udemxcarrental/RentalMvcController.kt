package hu.danielwolf.udemxcarrental

import hu.danielwolf.udemxcarrental.car.CarService
import hu.danielwolf.udemxcarrental.models.SearchModel
import hu.danielwolf.udemxcarrental.rental.RentalService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class RentalMvcController (val carService: CarService, val rentalService: RentalService) {

    @GetMapping("/search")
    fun searchRentalForm(model: Model): String {
        model["title"] = "Car Rental"
        model["search"] = SearchModel()
        return "searchrentalform"
    }

    @PostMapping("/search")
    fun searchRentalResult(@ModelAttribute search: SearchModel, model: Model): String? {
        val cars = rentalService.getCarsByDate(search)
        model["title"] = "Car Rental"
        model["cars"] = cars
        model["daterange"] = search
        return "searchrentalresult"
    }
}