package life.mosu.mosuserver.domain.school;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolJpaRepository extends JpaRepository<SchoolJpaEntity, Long> {

    Optional<SchoolJpaEntity> findBySchoolNameAndAreaAndExamDate(String schoolName, Area area,
            LocalDate examDate);
}
