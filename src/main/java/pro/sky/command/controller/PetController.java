
package pro.sky.command.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.command.constants.KindOfPet;
import pro.sky.command.model.Pet;
import pro.sky.command.repository.OwnerRepository;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.service.PetService;

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
     * @see PetService#PetService(PetRepository, OwnerRepository)
     */
    public PetController(PetService service) {
        this.service = service;
    }

    /**
     * Функция получения всех питомцев, хранящихся в базе данных {@link PetService#getAll(KindOfPet)}
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
    public ResponseEntity<Collection<Pet>> findAll( @RequestParam KindOfPet kind) {
        return ResponseEntity.ok(service.getAll(kind));
    }

    /**
     * Функция добавления нового питомца в базу данных {@link PetService#addPet(String, KindOfPet, long)} )}
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
    public ResponseEntity<Object> addPet(@RequestParam String name,
                      @RequestParam KindOfPet kind, @RequestParam  long id) {
        Pet tar = service.findPet(id);
        if (tar != null) {
            return new ResponseEntity<>("Питомец с таким номером сушествует. " +
                    "Убедитесь в правильности заполнения или выберите другой запрос.", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.addPet(name,kind,id));
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

    public ResponseEntity<Object> getPetById(@PathVariable Long id) {

        Pet pet = service.findPet(id);
        if (pet == null) {
            return new ResponseEntity<>("Нет питомца с таким иномером", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(pet);
    }

    /**
     * Функция изменения существующего питомца в базе данных {@link PetService#updatePet(String, KindOfPet, Pet)}
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
    public ResponseEntity<Object> updatePet(@RequestParam(defaultValue = "  ") String name,
                                         @RequestParam(defaultValue = "null") KindOfPet kind,
                                         @RequestParam  long id) {
        Pet tar = service.findPet(id);
        if (tar == null) {
            return new ResponseEntity<>("Нет питомца с таким иномером", HttpStatus.BAD_REQUEST);
        }
        if (tar.getOwner()!= null) {
            return new ResponseEntity<>("Нельзя изменять питомца у которого есть хозяин." +
                    " Если владелец не прошел испытательный срок выберите Установить владельца", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.updatePet(name,kind,tar));
    }
    @Operation(summary = "Функция изменения владельца питомца.",
            responses = {
                    @ApiResponse(
                            responseCode = "200")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Изменяем владельца",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ))
    @PutMapping("/{petId}")  //PUT http://localhost:8080/pet/{petId}/{ownerId}
    public ResponseEntity<Object> updatePetOwner(@PathVariable long petId,
                                                     @RequestParam (defaultValue = "0")  Long ownerId) {
        Pet tar = service.setOwnerToPet(petId,ownerId);
        if (tar == null) {
            return new ResponseEntity<>("Нет владельца или питомца с указаным номером. Уточните данные ", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(tar);
    }
}
