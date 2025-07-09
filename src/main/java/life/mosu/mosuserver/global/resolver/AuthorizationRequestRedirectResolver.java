package life.mosu.mosuserver.global.resolver;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationRequestRedirectResolver implements OAuth2AuthorizationRequestResolver {

    public static final String REDIRECT_PARAM_KEY = "redirect";
    private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        return addRedirectState(defaultResolver.resolve(request), request);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(
            HttpServletRequest request,
            String clientRegistrationId
    ) {
        return addRedirectState(
                defaultResolver.resolve(request, clientRegistrationId),
                request
        );
    }

    private OAuth2AuthorizationRequest addRedirectState(
            OAuth2AuthorizationRequest authorizationRequest,
            HttpServletRequest request
    ) {
        if (authorizationRequest == null) {
            return null;
        }

        String redirect = request.getParameter(REDIRECT_PARAM_KEY);
        if (!UrlUtils.isValidRedirectUrl(redirect)) {
            log.debug("Invalid redirect URL: {}", redirect);
            return authorizationRequest;
        }

        log.debug("Adding redirect state to authorization request: {}", redirect);

        return OAuth2AuthorizationRequest.from(authorizationRequest)
                .state(appendToState(authorizationRequest.getState(),
                        Map.of(REDIRECT_PARAM_KEY, redirect)))
                .build();
    }

    private String appendToState(
            String state,
            Map<String, String> additionalParams
    ) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        if (state != null) {
            uriBuilder.query(state);
        }
        additionalParams.forEach(uriBuilder::queryParam);
        return uriBuilder.build().getQuery();
    }
}