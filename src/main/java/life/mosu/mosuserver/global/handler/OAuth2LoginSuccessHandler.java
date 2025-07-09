package life.mosu.mosuserver.global.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.mosu.mosuserver.application.auth.AccessTokenService;
import life.mosu.mosuserver.application.oauth.OAuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AccessTokenService accessTokenService;
    @Value("${target.url}")
    private String targetUrl;

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication
    ) throws IOException {
        final OAuthUser oAuthUser = (OAuthUser) authentication.getPrincipal();

        final String accessToken = accessTokenService.generateAccessToken(oAuthUser.getUser());

        final String redirectUrlWithToken = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken)
                .build()
                .toUriString();

        log.info("로그인 성공. 리다이렉트 URL: {}", redirectUrlWithToken);

        response.sendRedirect(redirectUrlWithToken);
    }
}