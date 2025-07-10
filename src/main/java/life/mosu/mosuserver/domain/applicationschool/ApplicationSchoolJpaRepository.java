package life.mosu.mosuserver.domain.applicationschool;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationSchoolJpaRepository extends
        JpaRepository<ApplicationSchoolJpaEntity, Long> {


    boolean existsByUserIdAndSchoolId(Long userId, Long schoolId);

    List<ApplicationSchoolJpaEntity> findAllByApplicationId(Long applicationId);

    boolean existsByApplicationId(Long applicationId);

    @Query("SELECT COUNT(a) = :size FROM ApplicationSchoolJpaEntity a WHERE a.id IN :applicationSchoolIds")
    boolean existsAllByIds(@Param("applicationSchoolIds") List<Long> applicationSchoolIds,
            @Param("size") long size);
}
