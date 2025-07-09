package life.mosu.mosuserver.domain.application;

import java.util.List;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationSchoolJpaRepository extends
        JpaRepository<ApplicationSchoolJpaEntity, Long> {

    boolean existsByUserIdAndSchoolId(Long userId, Long schoolId);

    List<ApplicationSchoolJpaEntity> findAllByApplicationId(Long applicationId);

    boolean existsByApplicationId(Long applicationId);
}
