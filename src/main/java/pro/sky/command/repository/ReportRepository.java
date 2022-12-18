package pro.sky.command.repository;
/**
 * Репозиторий для сущности Report
  * @autor Наталья Шилова
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.command.model.Report;

import java.util.Optional;


public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * запрос для поиска отчета по идентификатору питомца и дате отчета
     *
     * @param data  дата отчета получается в сообщениипользователя
     * @param pet идентификатор питомца
     * @return возвращает сущность Report
     * @see Report
     */
    @Query(value = "SELECT r.* FROM report r inner join pets p on r.pet=p.id where r.pet=:pet and r.recording_date=:data", nativeQuery = true)
    Optional<Report> findByQuery(Long pet, String data);
}
