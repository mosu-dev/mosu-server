package life.mosu.mosuserver.domain.auth.security;

public interface RefreshTokenRepository {
    boolean existsByRefreshToken(String refreshToken);

    void save(RefreshToken refreshToken);

    boolean existsByUserId(Long id);

    void deleteByUserId(Long id);
}