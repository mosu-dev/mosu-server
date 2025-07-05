package life.mosu.mosuserver.persistence.application;

import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ApplicationSchoolJpaRepository extends JpaRepository<ApplicationSchoolJpaEntity, Long> {


    boolean existsByUserIdAndSchoolIdIn(Long userId, Collection<Long> schoolIds);

    List<ApplicationSchoolJpaEntity> findAllByApplicationId(Long applicationId);

    boolean existsByApplicationId(Long applicationId);
}
