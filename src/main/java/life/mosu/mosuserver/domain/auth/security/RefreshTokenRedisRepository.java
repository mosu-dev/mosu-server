package life.mosu.mosuserver.domain.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRedisRepository implements RefreshTokenRepository {

    private final RefreshTokenKeyValueRepository repository;

    @Override
    public boolean existsByRefreshToken(final String refreshToken) {
        return repository.existsByRefreshToken(refreshToken);
    }

    @Override
    public void save(final RefreshToken refreshToken) {
        final RefreshTokenRedisEntity entity = RefreshTokenRedisEntity.from(refreshToken);
        repository.save(entity);
    }

    @Override
    public boolean existsByUserId(final Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteByUserId(final Long id) {
        repository.deleteById(id);
    }
}