package life.mosu.mosuserver.domain.school;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "school")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
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

    @Builder
    public SchoolJpaEntity(String schoolName, Area area, AddressJpaVO address, LocalDate examDate,
            Long capacity) {
        this.schoolName = schoolName;
        this.area = area;
        this.address = address;
        this.examDate = examDate;
        this.capacity = capacity;
    }
}
