package life.mosu.mosuserver.applicaiton.auth;

import io.jsonwebtoken.Claims;
import life.mosu.mosuserver.domain.auth.security.RefreshToken;
import life.mosu.mosuserver.domain.auth.security.RefreshTokenRepository;
import life.mosu.mosuserver.domain.user.UserJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService extends JwtTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenService(
        @Value("${jwt.refresh-token.expire-time}") final Long expireTime,
        @Value("${jwt.secret}") final String secretKey,
        final UserJpaRepository userJpaRepository,
        final RefreshTokenRepository refreshTokenRepository
    ) {
        super(expireTime, secretKey,"Refresh", "Refresh-Token", userJpaRepository);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void cacheRefreshToken(final Long userId, final String refreshToken) {
        refreshTokenRepository.save(
            RefreshToken.of(
                userId,
                refreshToken,
                expireTime
            )
        );
    }

    @Override
    protected Claims validateAndParseToken(final String token) {
        if (!refreshTokenRepository.existsByRefreshToken(token)) {
            throw new CustomRuntimeException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        return super.validateAndParseToken(token);
    }

    public void deleteRefreshToken(final Long id) {
        if (!refreshTokenRepository.existsByUserId(id)) {
            throw new CustomRuntimeException(ErrorCode.INVALID_TOKEN);
        }
        refreshTokenRepository.deleteByUserId(id);
    }
}