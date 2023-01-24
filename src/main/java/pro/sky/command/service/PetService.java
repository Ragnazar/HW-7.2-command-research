/**
 * Сервис для работы с репозиорием <b>pet</b>.
 *
 * @autor Иван Авдеев
 * @version 0.1
 */
package pro.sky.command.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.command.constants.Const;
import pro.sky.command.constants.KindOfPet;
import pro.sky.command.model.Owner;
import pro.sky.command.repository.OwnerRepository;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.model.Pet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
public class PetService {
    /**
     * Поле репозитория питомцев
     */
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    /**
     * Поле для подключения сервиса логирования
     */
    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    /**
     * Конструктор - создание нового объекта репозитория
     *
     * @see PetService#petRepository
     */
    public PetService(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    /**
     * Функция добавления нового питомца в базу данных {@link PetRepository#save(Object)}
     *
     * @return возвращает объект, содержащий данные добавленного питомца
     */
    public Pet addPet(String name, KindOfPet kind, long id) {
        logger.info("Was invoked method for add a new pet");
        Pet pet = petRepository.findById(id).orElse(new Pet(id, name, kind));
        return petRepository.save(pet);
    }

    /**
     * Функция получения питомца из базы данных по его идентификатору {@link PetRepository#findById(Object)}
     *
     * @return возвращает объект, содержащий данные найденного питомца
     */
    public Pet findPet(long id) {
        logger.info("Was invoked method for find pet by id");
        return petRepository.findById(id).orElse(null);
    }

    /**
     * Функция изменения существующего питомца в базе данных {@link PetRepository#save(Object)}
     *
     * @return возвращает объект, содержащий данные измененного питомца
     */
    public Pet updatePet(String name, KindOfPet kind, Pet pet) {
        logger.info("Was invoked method for edit pet");
        if (StringUtils.isBlank(name)) {
            pet.setNamePet(name);
        }
        if (kind != null) {
            pet.setKindOfAnimal(kind);
        }
        return petRepository.save(pet);
    }

    public Pet setOwnerToPet(long petId, Long ownerId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet == null) {
            return null;
        }
        if (ownerId == 0) {
            pet.setCorrectReportCount(0);
            pet.setDateLastCorrectReport(null);
            pet.setOwner(null);
        } else {
            Owner owner = ownerRepository.findById(String.valueOf(ownerId)).orElse(null);
            if (owner == null) {
                return null;
            }
            pet.setDateLastCorrectReport(LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern(Const.PATTERN_LOCAL_DATA)));
            pet.setOwner(owner);
        }
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
     *
     * @return возвращает список всех питомцев
     */
    public Collection<Pet> getAll(KindOfPet kind) {
        logger.info("Was invoked method for get a list of all pets");
        return petRepository.findAllByKindOfAnimal(kind.name());
    }

}
