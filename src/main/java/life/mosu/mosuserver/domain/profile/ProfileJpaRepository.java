package life.mosu.mosuserver.domain.profile;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJpaRepository extends JpaRepository<ProfileJpaEntity, Long> {

    boolean existsByUserId(Long userId);

    Optional<ProfileJpaEntity> findByUserId(Long userId);
}
