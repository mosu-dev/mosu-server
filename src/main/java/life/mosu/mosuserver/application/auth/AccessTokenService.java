package life.mosu.mosuserver.application.auth;

import life.mosu.mosuserver.domain.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService extends JwtTokenService {

    @Autowired
    public AccessTokenService(
        @Value("${jwt.access-token.expire-time}") final Long expireTime,
        @Value("${jwt.secret}") final String secretKey,
        final UserJpaRepository userRepositoy
    ) {
        super(expireTime, secretKey, "Access", "Authorization", userRepositoy);
    }
}