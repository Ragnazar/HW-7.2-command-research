package pro.sky.command.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.command.model.Pet;
import pro.sky.command.service.PetService;

import java.util.Collection;

@RestController
@RequestMapping("pet")
public class PetController {
    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @GetMapping(path = "all")  //GET http://localhost:8080/pet/all
    public ResponseEntity<Collection<Pet>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping //POST http://localhost:8080/pet
    public Pet addPet(Pet pet) {
        return service.addPet(pet);
    }

    @DeleteMapping(path = "{id}") //DELETE http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> removePet(@PathVariable Long id) {
        service.removePet(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "{id}")   //GET http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Pet pet = service.findPet(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @PutMapping  //PUT http://localhost:8080/pet/{id}
    public ResponseEntity<Pet> updateFaculty(@RequestBody Pet pet) {
        Pet tar = service.editPet(pet);
        if (tar == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(tar);
    }
}
