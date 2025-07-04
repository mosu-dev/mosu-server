package life.mosu.mosuserver.domain.subject;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subject")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubjectJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Column(name = "application_school_id")
    private Long applicationSchoolId;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private Subject subject;

    @Builder
    public SubjectJpaEntity(Long applicationSchoolId, Subject subject) {
        this.applicationSchoolId = applicationSchoolId;
        this.subject = subject;
    }
//    public void updateSubject(Subject subject) {
//        this.subject = subject;
//    }
}
