package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.command.model.Pet;
/**
 * Репозиторий для сущности Pet
 * хранятся данные питомце: уникальный номер, кличка, порода, список номеров отчетов.
 * @autor Лукин Дмитрий
 */
@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
}
