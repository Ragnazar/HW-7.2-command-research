package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.command.model.Pet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для сущности Pet
 * хранятся данные питомце: уникальный номер, кличка, порода, список номеров отчетов.
 * @autor Лукин Дмитрий
 */
@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    @Query(value = "SELECT p.* from pet p where p.owner_id=:ownerId",nativeQuery = true)
    List<Pet> findAllByOwner(String ownerId);

    @Query(value = "SELECT p.* from pet p where p.kind=:kind",nativeQuery = true)
    List<Pet> findAllByKindOfAnimal(String kind);

    @Query(value = "SELECT p.* from pet p where p.date_last_correct_report=:date",nativeQuery = true)
    List<Pet> findAllByDateLastCorrectReport(LocalDate date);

    @Query(value = "SELECT p.* from pet p where p.correct_report_count=:count",nativeQuery = true)
    Optional<Pet> findByCorrectReportCount(int count);

}
