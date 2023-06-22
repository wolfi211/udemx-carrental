package hu.danielwolf.udemxcarrental.services

import hu.danielwolf.udemxcarrental.repositories.CarRepository
import hu.danielwolf.udemxcarrental.models.CarEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException

@Service
class CarService(val repository: CarRepository) {

    fun getAll():List<CarEntity> = repository.findAll()

    fun getAllActive():List<CarEntity> = repository.findByActiveEquals(true)

    fun getById(id: Long): CarEntity = repository.findByIdOrNull(id) ?:
        throw ResponseStatusException(HttpStatus.NOT_FOUND)

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

    fun setCarImage(id: Long, file: MultipartFile){
        val car : CarEntity = repository.findById(id).orElseThrow()
        car.image = file.bytes
        repository.save(car)
    }

    fun getCarImage(id: Long): ByteArray? {
        val car: CarEntity = repository.findById(id).orElseThrow()
        return car.image
    }

    fun setActive(id: Long, active: Boolean): CarEntity {
        return if (repository.existsById(id)) {
            val car = getById(id)
            car.active = active
            repository.save(car)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}