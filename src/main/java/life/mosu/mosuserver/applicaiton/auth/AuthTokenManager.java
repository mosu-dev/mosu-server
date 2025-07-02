package life.mosu.mosuserver.applicaiton.auth;

import jakarta.servlet.http.HttpServletRequest;
import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.presentation.auth.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenManager {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    public Token generateAuthToken(final UserJpaEntity user) {
        final String accessToken = accessTokenService.generateJwtToken(user);
        final String refreshToken = refreshTokenService.generateJwtToken(user);

        refreshTokenService.cacheRefreshToken(user.getId(), refreshToken);

        return Token.of(JwtTokenService.BEARER_TYPE, accessToken, refreshToken);
    }

    public Token reissueAccessToken(final HttpServletRequest servletRequest) {
        final String refreshTokenString = refreshTokenService.resolveToken(servletRequest);

        final Authentication authentication = refreshTokenService.getAuthentication(refreshTokenString);
        final PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        final UserJpaEntity user = principalDetails.user();

        refreshTokenService.deleteRefreshToken(user.getId());

        return generateAuthToken(user);
    }
}