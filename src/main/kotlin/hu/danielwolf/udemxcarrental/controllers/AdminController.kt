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
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@RequestMapping("admin")
@Controller
class AdminController(val carService: CarService, val rentalService: RentalService){

    @RequestMapping(value = ["", "/"])
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
    fun newCar(model:RedirectAttributes, @ModelAttribute car: CarModel): String {
        return try {
            val newCar = CarEntity(dailyPrice = car.dailyPrice, licensePlateNumber = car.licensePlateNumber.toString())
            carService.create(newCar)
            model.addFlashAttribute("success","Car successfully added!")
            "redirect:/admin/cars"
        } catch (e: Exception) {
            model.addFlashAttribute("error", "Could not create a new car!")
            "redirect:/admin/cars/new"
        }
    }

    @GetMapping("/cars/{id}/edit")
    fun editCarForm(model: Model, @PathVariable id: Long): String {
        model["title"] = "Car Rental - Admin"
        model["car"] = carService.getById(id)
        return "admin/editcarform"
    }

    @PostMapping("/cars/{id}/edit")
    fun editCar(model: RedirectAttributes, @PathVariable id: Long, @ModelAttribute carModel: CarModel): String {
        return try {
            val car = carService.getById(id)
            car.licensePlateNumber = carModel.licensePlateNumber.toString()
            car.dailyPrice = carModel.dailyPrice
            carService.update(id, car)
            model.addFlashAttribute("success", "Saved edited car!")
            "redirect:/admin/cars"
        } catch (e: Exception) {
            model.addFlashAttribute("error", "Could not edit car!")
            "redirect:/admin/cars"
        }
    }

    @GetMapping("/cars/{id}/image")
    fun editCarImageForm(model: Model, @PathVariable id: Long): String {
        model["title"] = "Car Rental - Admin"
        model["id"] = id
        return "admin/newimageform"
    }

    @PostMapping("/cars/{id}/image")
    fun editCarImage(model: RedirectAttributes, @PathVariable id: Long, @RequestParam("image") image: MultipartFile): String {
        return try {
            carService.setCarImage(id, image)
            model.addFlashAttribute("success", "Saved image!")
            "redirect:admin/carlist"
        } catch(e: Exception) {
            model.addFlashAttribute("error","Could not save image!")
            "redirect:admin/carlist"
        }
    }

    @GetMapping("/cars/{id}/deactivate")
    fun deactivateCar(model: RedirectAttributes, @PathVariable id: Long): String {
        return try {
            rentalService.setCarActive(id, false)
            model.addFlashAttribute("success", "Car deactivated!")
            "redirect:/admin/cars"
        } catch(e: Exception) {
            model.addFlashAttribute("error", "Could not deactivate car!")
            "redirect:/admin/cars"
        }
    }

    @GetMapping("/cars/{id}/activate")
    fun activateCar(model: RedirectAttributes, @PathVariable id: Long): String {
        return try {
            rentalService.setCarActive(id, true)
            model.addFlashAttribute("success", "Car activated!")
            "redirect:/admin/cars"
        } catch(e: Exception) {
            model.addFlashAttribute("error", "Could not activate car!")
            "redirect:/admin/cars"
        }
    }
}