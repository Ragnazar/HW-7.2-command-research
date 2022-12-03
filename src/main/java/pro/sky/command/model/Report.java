package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
/**
 * Класс ежедневного отчета для питомника о питомце со свойствами <b>photo</b>, <b>diet</b>,
 * <b>state_of_health</b>, <b>behavior_changes</b>.
 * @autor Иван Авдеев
 * @version 0.1
 */
@Entity
@Table(name = "report")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Report {
    /** Поле идентификатор отчёта */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /** Поле идентификатор питомца */
    @NotBlank
    @Column(name = "pet_id")
    private Long petId;

    /** Поле даты заполнения*/
    @NotBlank
    @CreatedDate
    @Column(name = "recording_date", nullable = false)
    private Long recordingDate;

    /** Поле с фотографией питомца*/
    @Column(name = "photo")
    @Lob
    private byte[] data;

    /** Поле рациона питания питомца*/
    @Column(name = "diet")
    private String diet;

    /** Поле состояния здоровья питомца*/
    @Column(name = "state_of_health")
    private String stateOfHealth;

    /** Поле описания изменений в поедении и привычках питомца*/
    @Column(name = "behavior_changes")
    private String behaviorChanges;


}
