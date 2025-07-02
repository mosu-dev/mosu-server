package life.mosu.mosuserver.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
