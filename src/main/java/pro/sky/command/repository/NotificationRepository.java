package pro.sky.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.command.model.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
@Query("SELECT n FROM Notification n where n.text=:text")
    List<Notification> findAllByText(String text);
}
