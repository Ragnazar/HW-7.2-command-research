package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.command.model.Report;

import java.util.Optional;


public interface ReportRepository extends JpaRepository<Report,Long> {
    @Query(value = "SELECT r.* FROM report r inner join pet p on r.pet=p.pet_id where r.pet=:pet and r.recording_date=:data", nativeQuery = true)
    Optional<Report> findByQuery(Long pet, String data);
}
