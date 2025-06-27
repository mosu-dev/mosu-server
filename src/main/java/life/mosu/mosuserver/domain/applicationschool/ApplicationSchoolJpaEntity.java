package life.mosu.mosuserver.domain.applicationschool;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import life.mosu.mosuserver.domain.school.AddressJpaVO;
import life.mosu.mosuserver.domain.school.Area;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="application_school")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationSchoolJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="application_school_id")
    private Long id;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "application_school_name")
    private String schoolName;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Embedded
    private AddressJpaVO address;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Column(name = "capacity")
    private Long capacity;
}
