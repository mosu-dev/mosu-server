package life.mosu.mosuserver.domain.school;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SchoolRepository extends JpaRepository<SchoolJpaEntity, Long> {

    @Query("""
                SELECT new life.mosu.mosuserver.domain.school.SchoolApplicationProjection(s.id, s.schoolName, COUNT(a))
                FROM SchoolJpaEntity s
                LEFT JOIN ApplicationSchoolJpaEntity a ON a.schoolId = s.id
                GROUP BY s.id, s.schoolName
            """)
    List<SchoolApplicationProjection> countBySchoolNameGroupBy();
}