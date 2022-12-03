package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "pets")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.BIGINT)
    @Column(name = "id")
    @NotBlank
    private Long id;
    @Column(name = "name_pet")
    @NotBlank
    private String namePet;
    @JoinColumn(name = "owner_id")
    @OneToOne(optional = false, mappedBy = "pet")
    private Owner owner;
    @NotBlank
    @Column(name ="kind")
    private String kindOfAnimal;
    @NotBlank
    @Column(name = "report_id")
    private Long report_id;
}
