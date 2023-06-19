package hu.danielwolf.udemxcarrental

import hu.danielwolf.udemxcarrental.car.CarEntity
import hu.danielwolf.udemxcarrental.car.CarService
import hu.danielwolf.udemxcarrental.models.CarModel
import hu.danielwolf.udemxcarrental.rental.RentalService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("admin")
@Controller
class AdminMvcController(val carService: CarService){

    @GetMapping("/")
    fun adminLandingPage(model: Model): String {
        model["title"] = "Car Rental - Admin"
        return "adminlandingpage"
    }

    @GetMapping("/rentals")
    fun rentalList(model: Model): String {
        model["title"] = "Car Rental - Admin"
        return "adminrentallist"
    }

    @GetMapping("/cars")
    fun carList(model:Model): String {
        model["title"] = "Car Rental - Admin"
        model["cars"] = carService.getAll()
        return "admincarlist"
    }

    @GetMapping("/cars/new")
    fun newCarForm(model:Model): String {
        model["title"] = "Car Rental - Admin"
        model["car"] = CarModel()
        return "adminnewcarform"
    }

    @PostMapping("/cars/new")
    fun newCar(model:Model, @ModelAttribute car: CarModel): String {
        var newCar = CarEntity(dailyPrice = car.dailyPrice, licensePlateNumber = car.licensePlateNumber.toString())
        carService.create(newCar)
        return "success"
    }

    @GetMapping("/cars/{id}")
    fun editCar(model: Model, @PathVariable id: Long): String {
        model["title"] = "Car Rental - Admin"
        return "admineditcarform"
    }
}