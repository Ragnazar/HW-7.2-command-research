package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.command.model.Pet;

import java.util.List;

/**
 * Репозиторий для сущности Pet
 * хранятся данные питомце: уникальный номер, кличка, порода, список номеров отчетов.
 * @autor Лукин Дмитрий
 */
@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    @Query(value = "SELECT p.* from pet p where p.owner_id=:ownerId",nativeQuery = true)
    List<Pet> findAllByOwner(String ownerId);

}
