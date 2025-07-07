package life.mosu.mosuserver.application.auth;

import jakarta.servlet.http.HttpServletRequest;
import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.auth.dto.LoginRequest;
import life.mosu.mosuserver.presentation.auth.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthTokenManager authTokenManager;

    @Transactional
    public Token login(final LoginRequest request) {
        try {
            final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.id(), request.password());
            final Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
            final PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            final UserJpaEntity user = principalDetails.user();

            return authTokenManager.generateAuthToken(user);
        } catch (final Exception e) {
            throw new CustomRuntimeException(ErrorCode.INCORRECT_ID_OR_PASSWORD);
        }
    }

    @Transactional
    public Token reissueAccessToken(final HttpServletRequest servletRequest) {
        return authTokenManager.reissueAccessToken(servletRequest);
    }
}
