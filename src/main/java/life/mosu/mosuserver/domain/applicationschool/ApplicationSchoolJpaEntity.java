package life.mosu.mosuserver.domain.applicationschool;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import life.mosu.mosuserver.domain.school.AddressJpaVO;
import life.mosu.mosuserver.domain.school.Area;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application_school")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationSchoolJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_school_id")
    private Long id;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "application_school_name")
    private String schoolName;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Embedded
    private AddressJpaVO address;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Enumerated(EnumType.STRING)
    private Lunch lunch;

    @Column(name = "examination_number")
    private String examinationNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "exam_subject", joinColumns = @JoinColumn(name = "application_school_id"))
    @Enumerated(EnumType.STRING)
    private Set<Subject> subjects = new HashSet<>();


    @Builder
    public ApplicationSchoolJpaEntity(
        final Long userId,
        final Long applicationId,
        final Long schoolId,
        final String schoolName,
        final Area area,
        final AddressJpaVO address,
        final LocalDate examDate,
        final Lunch lunch,
        final String examinationNumber,
        final Set<Subject> subjects
    ) {
        this.userId = userId;
        this.applicationId = applicationId;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.area = area;
        this.address = address;
        this.examDate = examDate;
        this.lunch = lunch;
        this.examinationNumber = examinationNumber;
        this.subjects = subjects;
    }

    public void generateExaminationNumber() {

    }

    public void updateSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
