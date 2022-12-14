package pro.sky.command.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Класс для временной записи обращений в чат волонтеров
 * @autor Наталья Шилова
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    /**
     * Поле идентификатор. в него записывается идентификатор чата из которого послано сообщение
     */
    @Id
    private Long id;
    /**
     * Поле текст обращения к волонтеру. по нему находится сообщение для обратной отправки.
     */
    private String text;
    /**
     * Поле идентификатор сообщения. Используется, чтоб отправить обратное сообщение ответом на посланное
     */
    private int messageId;

}
