package hu.danielwolf.udemxcarrental.car

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CarService(val repository: CarRepository) {

    fun getAll():List<CarEntity> = repository.findAll()

    fun getById(id: Long): CarEntity = repository.findById(id).orElse(null)
        //?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun create(car: CarEntity): CarEntity = repository.save(car)

    fun delete(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun update(id: Long, car: CarEntity): CarEntity {
        return if (repository.existsById(id)) {
            car.id = id
            repository.save(car)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}