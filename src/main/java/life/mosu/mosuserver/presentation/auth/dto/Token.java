package life.mosu.mosuserver.presentation.auth.dto;

public record Token(
    String grantType,
    String accessToken,
    String refreshToken
) {
    public static Token of(
        String grantType,
        String accessToken,
        String refreshToken
    ) {
        return new Token(grantType, accessToken, refreshToken);
    }
}