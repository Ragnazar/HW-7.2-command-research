
package pro.sky.command.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.checkerframework.checker.fenum.qual.SwingHorizontalOrientation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.command.model.Pet;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.service.PetService;

import javax.persistence.Column;
import java.util.Collection;
/**
 * Контроллер для работы с базой данных <b>pet</b>.
 * @autor Иван Авдеев
 * @version 0.1
 */

@RestController
@RequestMapping("pet")
public class PetController {
    /**
     * Поле сервиса питомцев
     */
    private final PetService service;

    /**
     * Конструктор - создание нового объекта сервиса
     *
     * @see PetService#PetService(PetRepository)
     */
    public PetController(PetService service) {
        this.service = service;
    }

    /**
     * Функция получения всех питомцев, хранящихся в базе данных {@link PetService#getAll()}
     *
     * @return возвращает список всех питомцев
     */
    @Operation(summary = "Функция получения всех питомцев, хранящихся в базе данных, возвращает список всех питомцев.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Функция получения всех питомцев, хранящихся в базе данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Collection.class))
                            )
                    )
            })
    @GetMapping(path = "all")  //GET http://localhost:8080/pet/all
    public ResponseEntity<Collection<Pet>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }


    /**
     * Функция добавления нового питомца в базу данных {@link PetService#addPet(Pet)}
     *
     * @return возвращает объект, содержащий данные добавленного питомца
     */
    @Operation(summary = "Функция добавления нового питомца в базу данных, возвращает объект, содержащий данные добавленного питомца.",
            responses = {
                    @ApiResponse(
                            responseCode = "200")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавляем питомца в базу данных",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ))
    @PostMapping //POST http://localhost:8080/pet
    public Pet addPet(@RequestBody Pet pet) {
        return service.addPet(pet);
    }

    /**
     * Функция удаляет питомца из базы данных {@link PetService#removePet(long)}
     *
     * @return возвращает объект,содержащий данные удаленного питомца
     */
    @Operation(summary = "Функция удаляет питомца из базы данных, возвращает объект,содержащий данные удаленного питомца.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Функция удаляет питомца из базы данных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            })
    @DeleteMapping(path = "{id}") //DELETE http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> removePet(@Parameter(description = "Передаем ID питомца, подлежащего удалению") @PathVariable Long id) {
        service.removePet(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Функция получения питомца из базы данных по его идентификатору {@link PetService#findPet(long)}
     *
     * @return возвращает объект, содержащий данные найденного питомца
     */
    @Operation(summary = "Функция получения питомца из базы данных по его идентификатору, возвращает объект, содержащий данные найденного питомца.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Функция получения питомца из базы данных по его идентификатору",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            })
    @GetMapping(path = "{id}")   //GET http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> getPetById(@Parameter(description = "Передаем ID нужного нам питомца") @PathVariable Long id) {
        Pet pet = service.findPet(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    /**
     * Функция изменения существующего питомца в базе данных {@link PetService#editPet(Pet)}
     *
     * @return возвращает объект, содержащий данные измененного питомца
     */
    @Operation(summary = "Функция изменения существующего питомца в базе данных, возвращает объект, содержащий данные измененного питомца.",
            responses = {
                    @ApiResponse(
                            responseCode = "200")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Функция изменения питомца",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            )
    )
    @PutMapping  //PUT http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> updateFaculty(@RequestBody Pet pet) {
        Pet tar = service.editPet(pet);
        if (tar == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(tar);
    }
}
