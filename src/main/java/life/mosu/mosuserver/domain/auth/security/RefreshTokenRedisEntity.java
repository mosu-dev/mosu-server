package life.mosu.mosuserver.domain.auth.security;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "token_verification")
public class RefreshTokenRedisEntity {

    @Id
    private Long userId;

    @Indexed
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public static RefreshTokenRedisEntity from(final RefreshToken token) {
        final RefreshTokenRedisEntity entity = new RefreshTokenRedisEntity();
        entity.userId = token.userId();
        entity.refreshToken = token.refreshToken();
        entity.expiration = token.expiration();
        return entity;
    }
}