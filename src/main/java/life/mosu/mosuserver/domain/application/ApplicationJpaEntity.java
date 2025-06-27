package life.mosu.mosuserver.domain.application;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application")
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "application_school", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "school_id", nullable = false)
    private Set<Long> schoolIds = new HashSet<>();

    @Builder
    public ApplicationJpaEntity(
        final Long userId,
        final Lunch lunch,
        final String examinationNumber,
        final Set<Subject> subjects,
        final Set<Long> schoolIds
    ) {
        this.userId = userId;
        this.lunch = lunch;
        this.examinationNumber = examinationNumber;

        if (subjects == null || subjects.size() != 5) {
            throw new IllegalArgumentException("과목은 5개를 선택해야 합니다.");
        }
        this.subjects = subjects;

        if (schoolIds == null || schoolIds.isEmpty()) {
            throw new IllegalArgumentException("최소 한 개 이상의 학교를 선택해야 합니다.");
        }
        this.schoolIds = schoolIds;
    }

}
