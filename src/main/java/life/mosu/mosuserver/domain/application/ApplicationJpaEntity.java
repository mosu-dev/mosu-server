package life.mosu.mosuserver.domain.application;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private int Id;

    @Column(name ="user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Lunch lunch;

    @Column(name = "examination")
    private String examinationNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "exam_subject", joinColumns = @JoinColumn(name = "application_id"))
    @Enumerated(EnumType.STRING)
    private Set<Subject> subjects = new HashSet<>();

    @Builder
    public ApplicationJpaEntity(
        final Long userId,
        final Lunch lunch,
        final String examinationNumber,
        final Set<Subject> subjects
    ) {
        this.userId = userId;
        this.lunch = lunch;
        this.examinationNumber = examinationNumber;
        this.subjects = subjects;


    }

}
