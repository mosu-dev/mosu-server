package life.mosu.mosuserver.applicaiton.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import life.mosu.mosuserver.domain.user.OAuthUserJpaEntity;
import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.domain.user.UserJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;

@Slf4j
public abstract class JwtTokenService {

    protected static final String TOKEN_TYPE_KEY = "type";
    protected static final String BEARER_TYPE = "Bearer";

    protected final Long expireTime;
    protected final Key key;
    protected final String tokenType;
    protected final String header;
    protected final UserJpaRepository userRepository;

    protected JwtTokenService(
        final Long expireTime,
        final String secretKey,
        final String tokenType,
        final String header,
        final UserJpaRepository userJpaRepository
    ) {
        this.expireTime = expireTime;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.tokenType = tokenType;
        this.header = header;
        this.userRepository = userJpaRepository;
    }

    /**
     * JWT 토큰을 생성한다.
     *
     * @param user 토큰을 생성할 회원
     * @return 생성된 토큰
     */
    public String generateJwtToken(final UserJpaEntity user) {
        final long now = System.currentTimeMillis();
        final Date expireTime = new Date(now + this.expireTime);

        return Jwts.builder()
            .setSubject(user.getLoginId())
            .claim(TOKEN_TYPE_KEY, tokenType)
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * JWT 토큰을 생성한다.
     *
     * @param user 토큰을 생성할 회원
     * @return 생성된 토큰
     */
    public String generateAccessToken(final OAuthUserJpaEntity user) {
        final long now = System.currentTimeMillis();
        final Date expireTime = new Date(now + this.expireTime);

        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim(TOKEN_TYPE_KEY, tokenType)
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * JWT 토큰을 파싱하여 Authentication 객체를 생성한다.
     *
     * @param token 토큰
     * @return 생성된 Authentication
     */
    public Authentication getAuthentication(final String token) {
        final Claims claims = validateAndParseToken(token);
        final String loginId = claims.getSubject();
        final UserJpaEntity user = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        final PrincipalDetails principalDetails = new PrincipalDetails(user);

        return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException exception) {
            return exception.getClaims();
        }
    }

    /**
     * 토큰 검증과 함께 파싱을 수행한다.
     *
     * @param token 토큰
     * @return 파싱된 Claims
     */
    protected Claims validateAndParseToken(final String token) {
        try {
            final Claims claims = parseClaims(token);

            if (!claims.get(TOKEN_TYPE_KEY).equals(tokenType)) {
                throw new CustomRuntimeException(ErrorCode.INVALID_TOKEN_TYPE);
            }

            if (claims.getExpiration().toInstant().isBefore(new Date().toInstant())) {
                throw new CustomRuntimeException(ErrorCode.EXPIRED_TOKEN);
            }
            return claims;
        } catch (JwtException | IllegalArgumentException exception) {
            throw new CustomRuntimeException(ErrorCode.INVALID_TOKEN_TYPE);
        }
    }

    /**
     * HttpServletRequest에서 토큰을 추출한다. 토큰이 없는 경우 null을 반환한다.
     *
     * @param request HttpServletRequest
     * @return 추출된 토큰
     */
    public String resolveToken(final HttpServletRequest request) {
        final String header = request.getHeader(this.header);

        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.replace(BEARER_TYPE, "").trim();
        }
        return null;
    }
}
