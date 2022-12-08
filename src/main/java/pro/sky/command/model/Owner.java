package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Owner {
    @Id
    @Column(nullable = false)
    private String chatId;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String shelterButton;

    private boolean volunteerChat;

    @Column(name = "pet_id")
    private Long petId;

    public Owner(Long chatId, String name) {
        this.chatId = chatId.toString();
        this.name = name;
    }
}
