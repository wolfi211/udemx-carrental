package hu.danielwolf.udemxcarrental.car

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("api/v1/cars")
@RestController
class CarRestController(val service: CarService) {

    @GetMapping
    fun getAllCars() = service.getAll()

    @GetMapping("/{id}")
    fun getCar(@PathVariable id: Long) = service.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveCar(@RequestBody car: CarEntity): CarEntity = service.create(car)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCar(@PathVariable id: Long) = service.delete(id)

    @PutMapping("/{id}")
    fun updateCar(
        @PathVariable id: Long, @RequestBody car: CarEntity
    ) = service.update(id, car)
}