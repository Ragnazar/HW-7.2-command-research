package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;


@Entity
@Table(name = "owner")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Owner {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @NotBlank
    @Column(name ="chat_id", nullable = false)
    private String chatId;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank
    @OneToOne(optional = false)
    @JoinColumn(name = "pet_id", unique = true, nullable = false, updatable = false)
    private Pet pet;
}
