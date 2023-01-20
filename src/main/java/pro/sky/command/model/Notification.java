package pro.sky.command.model;

import lombok.*;

import javax.persistence.Entity;

/**
 * Класс для временной записи обращений в чат волонтеров
 *
 * @autor Наталья Шилова
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Notification {
    /**
     * Поле идентификатор. в него записывается идентификатор чата из которого послано сообщение
     */
    private Long id;
    /**
     * Поле текст обращения к волонтеру. по нему находится сообщение для обратной отправки.
     */
    @javax.persistence.Id
    private String text;
    /**
     * Поле идентификатор сообщения. Используется, чтоб отправить обратное сообщение ответом на посланное
     */
    private int messageId;

    public Notification(Long chatId, String text, int messageId) {
        this.id = chatId;
        this.text = text;
        this.messageId = messageId;
    }


}
