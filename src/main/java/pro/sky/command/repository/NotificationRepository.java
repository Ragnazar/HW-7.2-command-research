package pro.sky.command.repository;
/**
 * Репозиторий для сущности Notification
 *
 * @autor Наталья Шилова
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.command.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * запрос для поиска записи по тексту сообщения
     * @param text текст сообщения
     * @return возвращает список сущностей Notification
     * @see Notification
     */
    @Query("SELECT n FROM Notification n where n.text=:text")
    Notification findByText(String text);
}
