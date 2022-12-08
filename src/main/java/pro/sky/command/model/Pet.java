package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "pets")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pet {
    /**
     * Поле идентификатор питомца
     */
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.BIGINT)
    @Column(name = "id")
    @NotBlank
    private Long id;
    /**
     * Поле имя питомца
     */
    @Column(name = "name_pet")
    @NotBlank
    private String namePet;
    /** Поле идентификатор принадлежности к определенному владельцу */
    @JoinColumn(name = "owner_id")
    @OneToOne(optional = false, mappedBy = "pet")
    private Owner owner;
    /** Поле вид питомца (кошка, собака) */
    @NotBlank
    @Column(name = "kind")
    private String kindOfAnimal;
    /** Поле идентификатор идентификатор отчета о питомце из таблицы report */
    @NotBlank
    @OneToOne(optional = false)
    @JoinColumn(name = "report_id", unique = true, nullable = false, updatable = false)
    private Report report;

}
