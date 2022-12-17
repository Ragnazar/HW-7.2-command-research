package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

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
    @ManyToOne(optional = false)
    private Owner owner;
    /** Поле вид питомца (кошка, собака) */
    @NotBlank
    @Column(name = "kind")
    private String kindOfAnimal;
    /** Поле идентификатор идентификатор отчета о питомце из таблицы report */

    @OneToMany(mappedBy = "pet")
    private List<Report> reports;

}
