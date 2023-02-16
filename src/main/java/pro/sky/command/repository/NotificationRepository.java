package pro.sky.command.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.command.model.Notification;
/**
 * Репозиторий для сущности Notification
 * временно хранятся не отработанные вопросы поступающие в чат волонтеров
 *
 * @autor Наталья Шилова
 */
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
