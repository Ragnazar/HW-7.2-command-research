package pro.sky.command.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.model.Pet;

import java.util.Collection;

@Service
public class PetService {
    private final PetRepository petRepository;

    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet) {
        logger.info("Was invoked method for add a new pet");
        return petRepository.save(pet);
    }

    public Pet findPet(long id) {
        logger.info("Was invoked method for find pet by id");
        return petRepository.findById(id).orElse(null);
    }

    public Pet editPet(Pet pet) {
        logger.info("Was invoked method for edit pet");
        return petRepository.save(pet);
    }

    public void removePet(long id) {
        logger.info("Was invoked method for delete pet");
        petRepository.deleteById(id);
    }

    public Collection<Pet> getAll() {
        logger.info("Was invoked method for get a list of all pets");
        return petRepository.findAll();
    }

}
