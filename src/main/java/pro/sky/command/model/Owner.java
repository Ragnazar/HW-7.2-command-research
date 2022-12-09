package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Owner {
    /** Поле идентификатор владельца */
    @Id
    @Column(nullable = false)
    private String chatId;

    /** Поле имя владельца */
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String shelterButton;

    private boolean volunteerChat;
@OneToMany(mappedBy="owner")
    @Column(name = "pets_id")
    private List<Pet> pets;

    public Owner(Long chatId, String name) {
        this.chatId = chatId.toString();
        this.name = name;
    }
}
