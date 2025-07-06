package life.mosu.mosuserver.domain.applicationschool;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationSchoolJpaRepository extends
        JpaRepository<ApplicationSchoolJpaEntity, Long> {


    boolean existsByUserIdAndSchoolIdIn(Long userId, Collection<Long> schoolIds);

    List<ApplicationSchoolJpaEntity> findAllByApplicationId(Long applicationId);

    boolean existsByApplicationId(Long applicationId);
}
