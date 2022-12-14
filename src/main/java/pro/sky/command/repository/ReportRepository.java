package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.command.model.Report;


public interface ReportRepository extends JpaRepository<Report,Long> {

}
