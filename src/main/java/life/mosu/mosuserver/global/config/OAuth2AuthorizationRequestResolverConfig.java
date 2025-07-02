package life.mosu.mosuserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;

@Configuration
public class OAuth2AuthorizationRequestResolverConfig {

    @Bean
    public DefaultOAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver(
        ClientRegistrationRepository clientRegistrationRepository
    ) {
        return new DefaultOAuth2AuthorizationRequestResolver(
            clientRegistrationRepository,
            OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
        );
    }
}