package hu.danielwolf.udemxcarrental.controllers

import hu.danielwolf.udemxcarrental.models.CarEntity
import hu.danielwolf.udemxcarrental.models.DateRangeModel
import hu.danielwolf.udemxcarrental.models.RentalEntity
import hu.danielwolf.udemxcarrental.services.CarService
import hu.danielwolf.udemxcarrental.services.RentalService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.time.LocalDate

@RequestMapping("api/v1")
@RestController
class CarrentalRestController(val carService: CarService, val rentalService: RentalService) {

    @GetMapping("/cars")
    fun getAllCars() = carService.getAllActive()

    @GetMapping("/cars/{start}/{end}")
    fun getAvailableCars(@PathVariable start: LocalDate, @PathVariable end: LocalDate) = rentalService.getCarsByDate(DateRangeModel(start = start, end = end))

    @GetMapping("/cars/{id}")
    fun getCar(@PathVariable id: Long) = carService.getById(id)

    @GetMapping("/cars/{id}/image")
    fun getImage(@PathVariable("id") id: Long): ResponseEntity<Any>{

        return try {
            val image: ByteArray? = carService.getCarImage(id)

            ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${System.currentTimeMillis()}\"")
                .body(image)

        } catch(error: NoSuchElementException){
            ResponseEntity
                .notFound()
                .build()
        }
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveCar(@RequestBody car: CarEntity): CarEntity = carService.create(car)

    @PostMapping(value= ["/cars/{id}/image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun setCarImage(@PathVariable("id") id: Long, @RequestParam file: MultipartFile): ResponseEntity<Void>{
        return try {
            carService.setCarImage(id, file)
            ResponseEntity
                .created(URI("/api/user/${id}/profile-picture"))
                .build()
        } catch(error: NoSuchElementException){
            ResponseEntity
                .notFound()
                .build()
        }
    }

    @PostMapping("/rentals")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRental(@RequestBody rental: RentalEntity): RentalEntity = rentalService.create(rental)

    @DeleteMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCar(@PathVariable id: Long) = carService.delete(id)

    @PutMapping("/cars/{id}")
    fun updateCar(
        @PathVariable id: Long, @RequestBody car: CarEntity
    ) = carService.update(id, car)
}