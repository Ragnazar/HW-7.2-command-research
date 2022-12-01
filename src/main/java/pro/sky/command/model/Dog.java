package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "dogs")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dog {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.BIGINT)
    @Column(name = "dog_id")
    @NotBlank
    private Long dogId;
    @Column(name = "name_dog")
    @NotBlank
    private String nameDog;
    @JoinColumn(name = "owner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;
}
