package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.command.model.Report;

import java.util.Optional;

/**
 * Репозиторий для сущности Report
 * Хранятся отчеты ежедневно присылаемые пользователем. Содержит Номер отчета(автогенерация), дата отчета, номер питомца, фото, рацион, привычки и здоровье питомца.
 *
 * @autor Наталья Шилова
 */

public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * запрос для поиска отчета по идентификатору питомца и дате отчета
     *
     * @param data  дата отчета получается в сообщениипользователя
     * @param pet идентификатор питомца
     * @return возвращает сущность Report
     * @see Report
     */
    @Query(value = "SELECT r.* FROM report r inner join pet p on r.pet=p.id where r.pet=:pet and r.recording_date=:data", nativeQuery = true)
    Optional<Report> findByQuery(Long pet, String data);
    @Query(value = "select r.*  from report r where r.check_report like false limit 1", nativeQuery = true)
   Report findAllUncheck();
}
