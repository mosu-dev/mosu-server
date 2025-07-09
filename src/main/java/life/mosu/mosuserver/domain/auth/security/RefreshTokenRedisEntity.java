package life.mosu.mosuserver.domain.auth.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "token_verification")
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class RefreshTokenRedisEntity {

    @Id
    private Long userId;

    @Indexed
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public static RefreshTokenRedisEntity from(final RefreshToken token) {
        return new RefreshTokenRedisEntity(token.userId(), token.refreshToken(), token.expiration());
    }
}