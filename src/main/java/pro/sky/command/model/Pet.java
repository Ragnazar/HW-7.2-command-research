package pro.sky.command.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(appliesTo = "pet")
@Getter
@Setter
public class Pet {
    /**
     * Поле идентификатор питомца
     */
    @javax.persistence.Id
    private Long id;
    /**
     * Поле имя питомца
     */
    @Column(name = "name_pet")
    private String namePet;

    /**
     * Поле идентификатор принадлежности к определенному владельцу
     */
    @JoinColumn(name = "owner_id")
    @ManyToOne(optional = false)
    private Owner owner;
    /**
     * Поле вид питомца (кошка, собака)
     */
    @Column(name = "kind")
    private String kindOfAnimal;
    /**
     * Поле идентификатор идентификатор отчета о питомце из таблицы report
     */

    @OneToMany(mappedBy = "pet")
    private List<Report> reports;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "namePet = " + namePet + ", " +
                "owner = " + owner + ", " +
                "kindOfAnimal = " + kindOfAnimal + ")";
    }
}
