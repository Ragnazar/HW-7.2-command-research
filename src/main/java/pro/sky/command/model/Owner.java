package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Класс владельцы питомца со свойствами <b>name</b> и <b>phone_number</b>.
 * @autor Иван Авдеев
 * @version 0.1
 */
@Entity
@Table(name = "owner")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Owner {
    /** Поле идентификатор владельца */
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    /** Поле идентификатор чата с владельцем */
    @NotBlank
    @Column(name ="chat_id", nullable = false)
    private String chatId;

    /** Поле имя владельца */
    @NotBlank
    @Column(nullable = false)
    private String name;

    /** Поле телефонный номер владельца*/
    @NotBlank
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    /** Поле идентификатор питомца из питомника */
    @NotBlank
    @OneToOne(optional = false)
    @JoinColumn(name = "pet_id", unique = true, nullable = false, updatable = false)
    private Pet pet;
}
