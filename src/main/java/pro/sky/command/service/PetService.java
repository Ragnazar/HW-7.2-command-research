/**
 * Сервис для работы с репозиорием <b>pet</b>.
 * @autor Иван Авдеев
 * @version 0.1
 */
package pro.sky.command.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.model.Pet;

import java.util.Collection;

@Service
public class PetService {
    /** Поле репозитория питомцев */
    private final PetRepository petRepository;

    /** Поле для подключения сервиса логирования */
    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    /**
     * Конструктор - создание нового объекта репозитория
     * @see PetService#petRepository
     */
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Функция добавления нового питомца в базу данных {@link PetRepository#save(Object)}
     * @return возвращает объект, содержащий данные добавленного питомца
     */
    public Pet addPet(Pet pet) {
        logger.info("Was invoked method for add a new pet");
        return petRepository.save(pet);
    }

    /**
     * Функция получения питомца из базы данных по его идентификатору {@link PetRepository#findById(Object)}
     * @return возвращает объект, содержащий данные найденного питомца
     */
    public Pet findPet(long id) {
        logger.info("Was invoked method for find pet by id");
        return petRepository.findById(id).orElse(null);
    }

    /**
     * Функция изменения существующего питомца в базе данных {@link PetRepository#save(Object)}
     * @return возвращает объект, содержащий данные измененного питомца
     */
    public Pet editPet(Pet pet) {
        logger.info("Was invoked method for edit pet");
        return petRepository.save(pet);
    }

    /**
     * Функция удаляет питомца из базы данных {@link PetRepository#deleteById(Object)}
     */
    public void removePet(long id) {
        logger.info("Was invoked method for delete pet");
        petRepository.deleteById(id);
    }

    /**
     * Функция получения всех питомцев, хранящихся в базе данных {@link PetRepository#findAll()}
     * @return возвращает список всех питомцев
     */
    public Collection<Pet> getAll() {
        logger.info("Was invoked method for get a list of all pets");
        return petRepository.findAll();
    }

}
