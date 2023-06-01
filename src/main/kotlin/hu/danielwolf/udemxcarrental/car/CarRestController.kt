package hu.danielwolf.udemxcarrental.car

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@RequestMapping("api/v1/cars")
@RestController
class CarRestController(val carService: CarService) {

    @GetMapping
    fun getAllCars() = carService.getAll()

    @GetMapping("/{id}")
    fun getCar(@PathVariable id: Long) = carService.getById(id)

    @GetMapping("/{id}/image")
    fun getProfilePicture(@PathVariable("id") id: Long): ResponseEntity<Any>{

        return try {
            val image: ByteArray = carService.getCarImage(id)

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveCar(@RequestBody car: CarEntity): CarEntity = carService.create(car)

    @PostMapping(value= ["/{id}/image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCar(@PathVariable id: Long) = carService.delete(id)

    @PutMapping("/{id}")
    fun updateCar(
        @PathVariable id: Long, @RequestBody car: CarEntity
    ) = carService.update(id, car)
}