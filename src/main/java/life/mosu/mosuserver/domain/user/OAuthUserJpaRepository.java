package life.mosu.mosuserver.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthUserJpaRepository extends JpaRepository<OAuthUserJpaEntity, Long> {
    boolean existsByEmail(String email);

   Optional<OAuthUserJpaEntity> findByEmail(String email);
}
