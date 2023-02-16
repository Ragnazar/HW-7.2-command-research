package pro.sky.command.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor
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
    @JsonIgnore
    private String shelterButton;
    /**
     * Поле для определения того, что из отчета хочет послать владелец питомца
     */
    @JsonIgnore
    private String reportButton;
    /**
     * Поле индикатор. Показывает что пользователь запросил помощь волонтера
     */
    @JsonIgnore
    private boolean volunteerChat;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
      private List<Pet> pets;

    public Owner(Long chatId, String name) {
        this.chatId = chatId.toString();
        this.name = name;
    }
}
