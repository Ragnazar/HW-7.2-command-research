package pro.sky.command.model;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * Класс ежедневного отчета для питомника о питомце со свойствами <b>photo</b>, <b>diet</b>,
 * <b>state_of_health</b>, <b>behavior_changes</b>.
 *
 * @version 0.1
 * @autor Иван Авдеев
 */
@Entity
@Table(appliesTo = "report")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Report {

    /**
     * Поле идентификатор отчёта
     */
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    /**
     * Поле даты заполнения
     */
    @Column(name = "recording_date")
    private String recordingDate;
    /**
     * Поле с фотографией питомца
     */
    @Column(name = "photo")
    private String pathToPhoto;
    /**
     * Поле рациона питания питомца
     */
    @Column(name = "diet")
    private String diet;
    /**
     * Поле состояния здоровья питомца
     */
    @Column(name = "state_of_health")
    private String stateOfHealth;
    /**
     * Поле описания изменений в поедении и привычках питомца
     */
    @Column(name = "behavior_changes")
    private String behaviorChanges;
    /**
     * Поле идентификатор питомца
     */
    @ManyToOne
    @JoinColumn(name = "pet")
    private Pet pet;

    public Report(String recordingDate, Pet pet) {
        this.recordingDate = recordingDate;
        this.pet = pet;
    }

}
