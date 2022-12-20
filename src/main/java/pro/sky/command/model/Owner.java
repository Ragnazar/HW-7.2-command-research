package pro.sky.command.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Класс для временной записи обращений в чат волонтеров
 *
 * @autor Иван Авдеев
 */
@Entity
@Getter
@Setter
@ToString
public class Owner {
    /**
     * Поле идентификатор владельца
     */
    @javax.persistence.Id
    @Column(nullable = false)
    private String chatId;

    /**
     * Поле имя владельца
     */
    @NotBlank
    @Column(nullable = false)
    private String name;
    /**
     * Поле номер телефона для связи
     */
    @Column(name = "phone_number")
    private String phoneNumber;
    /**
     * Поле для определения к какому приюту относится владелец
     */
    private String shelterButton;
    /**
     * Поле для определения того, что из отчета хочет послать владелецся владелец
     */
    private String reportButton;
    /**
     * Поле индикатор. Показывает что пользователь запросил помощь волонтера
     */
    private boolean volunteerChat;
    @OneToMany(mappedBy = "owner")
    @Column(name = "pets_id")
    @ToString.Exclude
    private List<Pet> pets;

    public Owner(Long chatId, String name) {
        this.chatId = chatId.toString();
        this.name = name;
    }

}
