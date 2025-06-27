package life.mosu.mosuserver.domain.school;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name="school")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="school_id")
    private Long id;

    @Column(name = "school_name")
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
