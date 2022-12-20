/**
 * Контроллер для работы с базой данных <b>pet</b>.
 * @autor Иван Авдеев
 * @version 0.1
 */

package pro.sky.command.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.command.model.Pet;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.service.PetService;

import java.util.Collection;

@RestController
@RequestMapping("pet")
public class PetController {
    /** Поле сервиса питомцев */
    private final PetService service;

    /**
     * Конструктор - создание нового объекта сервиса
     * @see PetService#PetService(PetRepository)
     */
    public PetController(PetService service) {
        this.service = service;
    }

    /**
     * Функция получения всех питомцев, хранящихся в базе данных {@link PetService#getAll()}
     * @return возвращает список всех питомцев
     */
    @GetMapping(path = "all")  //GET http://localhost:8080/pet/all
    public ResponseEntity<Collection<Pet>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }


    /**
     * Функция добавления нового питомца в базу данных {@link PetService#addPet(Pet)}
     * @return возвращает объект, содержащий данные добавленного питомца
     */
    @PostMapping //POST http://localhost:8080/pet
    public Pet addPet(@RequestBody Pet pet) {
        return service.addPet(pet);
    }

    /**
     * Функция удаляет питомца из базы данных {@link PetService#removePet(long)}
     * @return возвращает объект,содержащий данные удаленного питомца
     */
    @DeleteMapping(path = "{id}") //DELETE http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> removePet(@PathVariable Long id) {
        service.removePet(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Функция получения питомца из базы данных по его идентификатору {@link PetService#findPet(long)}
     * @return возвращает объект, содержащий данные найденного питомца
     */
    @GetMapping(path = "{id}")   //GET http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Pet pet = service.findPet(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    /**
     * Функция изменения существующего питомца в базе данных {@link PetService#editPet(Pet)}
     * @return возвращает объект, содержащий данные измененного питомца
     */
    @PutMapping  //PUT http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> updateFaculty(@RequestBody Pet pet) {
        Pet tar = service.editPet(pet);
        if (tar == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(tar);
    }
}
