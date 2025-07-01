package life.mosu.mosuserver.persistence.profile;

import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileJpaRepository extends JpaRepository<ProfileJpaEntity, Long> {

    boolean existsByUserId(Long userId);

    Optional<ProfileJpaEntity> findByUserId(Long userId);
}
