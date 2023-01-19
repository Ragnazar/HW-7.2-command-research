package pro.sky.command.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Класс для добавления пользователей приюта
 *
 * @autor Иван Авдеев
 */
@Entity
@Data
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
     * Поле для определения того, что из отчета хочет послать владелец питомца
     */
    private String reportButton;
    /**
     * Поле индикатор. Показывает что пользователь запросил помощь волонтера
     */
    private boolean volunteerChat;
    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<Pet> pets;

    public Owner(Long chatId, String name) {
        this.chatId = chatId.toString();
        this.name = name;
    }

    public Owner() {
    }
}
