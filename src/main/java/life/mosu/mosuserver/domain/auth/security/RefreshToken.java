package life.mosu.mosuserver.domain.auth.security;

public record RefreshToken(
    Long userId,
    String refreshToken,
    Long expiration
) {
    public static RefreshToken of(Long userId, String refreshToken, Long expiration) {
        return new RefreshToken(userId, refreshToken, expiration);
    }
}