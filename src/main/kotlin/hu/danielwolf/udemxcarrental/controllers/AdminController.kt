package hu.danielwolf.udemxcarrental.controllers

import hu.danielwolf.udemxcarrental.models.CarEntity
import hu.danielwolf.udemxcarrental.services.CarService
import hu.danielwolf.udemxcarrental.models.CarModel
import hu.danielwolf.udemxcarrental.services.RentalService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@RequestMapping("admin")
@Controller
class AdminController(val carService: CarService, val rentalService: RentalService){

    @GetMapping("/")
    fun adminLandingPage(model: Model): String {
        model["title"] = "Car Rental - Admin"
        return "adminlandingpage"
    }

    @GetMapping("/rentals")
    fun rentalList(model: Model): String {
        model["title"] = "Car Rental - Admin"
        model["rentals"] = rentalService.getAll()
        return "admin/rentallist"
    }

    @GetMapping("/cars")
    fun carList(model:Model): String {
        model["title"] = "Car Rental - Admin"
        model["cars"] = carService.getAll()
        return "admin/carlist"
    }

    @GetMapping("/cars/new")
    fun newCarForm(model:Model): String {
        model["title"] = "Car Rental - Admin"
        model["car"] = CarModel()
        return "admin/newcarform"
    }

    @PostMapping("/cars/new")
    fun newCar(model:Model, @ModelAttribute car: CarModel): String {
        var newCar = CarEntity(dailyPrice = car.dailyPrice, licensePlateNumber = car.licensePlateNumber.toString())
        carService.create(newCar)
        return "success"
    }

    @GetMapping("/cars/{id}/edit")
    fun editCarForm(model: Model, @PathVariable id: Long): String {
        model["title"] = "Car Rental - Admin"
        model["car"] = carService.getById(id)
        return "admin/editcarform"
    }

    @PostMapping("/cars/{id}/edit")
    fun editCar(model: Model, @PathVariable id: Long, @ModelAttribute carModel: CarModel): String {
        var car = carService.getById(id)
        car.licensePlateNumber = carModel.licensePlateNumber.toString()
        car.dailyPrice = carModel.dailyPrice
        carService.update(id, car)
        return "success"
    }

    @GetMapping("/cars/{id}/image")
    fun editCarImageForm(model: Model, @PathVariable id: Long): String {
        model["title"] = "Car Rental - Admin"
        model["id"] = id
        return "admin/newimageform"
    }

    @PostMapping("/cars/{id}/image")
    fun editCarImage(model: Model, @PathVariable id: Long, @RequestParam("image") image: MultipartFile): String {
        carService.setCarImage(id, image)
        return "admin/carlist"
    }
}