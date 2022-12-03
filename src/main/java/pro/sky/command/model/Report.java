package pro.sky.command.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "report")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "pet_id")
    private Long petId;

    @NotBlank
    @CreatedDate
    @Column(name = "recording_date", nullable = false)
    private Long recordingDate;



    @Column(name = "photo")
    @Lob
    private byte[] data;



    @Column(name = "diet")
    private String diet;



    @Column(name = "state_of_health")
    private String stateOfHealth;




    @Column(name = "behavior_changes")
    private String behaviorChanges;

}
