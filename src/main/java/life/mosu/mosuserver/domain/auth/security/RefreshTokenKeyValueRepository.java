package life.mosu.mosuserver.domain.auth.security;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface RefreshTokenKeyValueRepository extends KeyValueRepository<RefreshTokenRedisEntity, Long> {

    boolean existsByRefreshToken(String refreshToken);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);
}