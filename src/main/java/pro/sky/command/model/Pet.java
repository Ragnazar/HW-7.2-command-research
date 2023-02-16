package pro.sky.command.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Table;
import pro.sky.command.constants.KindOfPet;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(appliesTo = "pet")
@Data
@NoArgsConstructor
public class Pet {
    public Pet(@NonNull Long id, String namePet, KindOfPet kindOfAnimal) {
        this.id = id;
        this.namePet = namePet;
        this.kindOfAnimal = kindOfAnimal.toString();
        this.correctReportCount = 0;
    }

    /**
     * Поле идентификатор питомца
     */
    @Id
  @NonNull
    private Long id;
    /**
     * Поле имя питомца
     */
    @Column(name = "name_pet")
    @NotBlank
    private String namePet;

    /**
     * Поле идентификатор принадлежности к определенному владельцу
     */

    @JoinColumn(name = "owner_id")
    @ManyToOne
    private Owner owner;
    /**
     * Поле вид питомца (кошка, собака)
     */
    @Column(name = "kind")
   @NonNull
    private String kindOfAnimal;
    /**
     * Поле счетчик колличества правильно оформленных отчетов.
     */
    @Column(name = "correct_report_count")
    private int correctReportCount = 0;
    /**
     * Поле даты последнего правильно оформленного отчета.
     */
    @Column(name = "date_last_correct_report")
    private LocalDate dateLastCorrectReport;

    /**
     * Поле идентификатор идентификатор отчета о питомце из таблицы report
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "pet")
    private List<Report> reports;

    public void setKindOfAnimal(KindOfPet kindOfAnimal) {
        this.kindOfAnimal = kindOfAnimal.toString();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "namePet = " + namePet + ", " +
                "owner = "+owner+", " +
                "kindOfAnimal = " + kindOfAnimal + ")";
    }
}
