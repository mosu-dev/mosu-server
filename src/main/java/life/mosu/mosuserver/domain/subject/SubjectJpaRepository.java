package life.mosu.mosuserver.domain.subject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectJpaRepository extends JpaRepository<SubjectJpaEntity, Long> {
    List<SubjectJpaEntity> findByApplicationSchoolId(Long applicationSchoolId);

    List<SubjectJpaEntity> findAllByApplicationSchoolId(Long id);
}
