package hu.danielwolf.udemxcarrental

import hu.danielwolf.udemxcarrental.car.CarService
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
        return "searchrentalform"
    }

    @PostMapping("/search")
    fun searchRentalResult(@ModelAttribute searchRentalForm: SearchRentalForm, model: Model): String? {

        val cars = rentalService.getCarsByDate(searchRentalForm)
        model.addAttribute("cars", cars)
        return "searchrentalresult"
    }


}