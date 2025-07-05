package life.mosu.mosuserver.application.oauth;

import life.mosu.mosuserver.domain.user.OAuthUserJpaEntity;
import life.mosu.mosuserver.domain.user.OAuthUserJpaRepository;
import life.mosu.mosuserver.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

    private final OAuthUserJpaRepository userRepository;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User user = super.loadUser(userRequest);

        final Map<String, Object> oAuth2UserAttributes = user.getAttributes();
        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        final OAuthUserInfo userInfo = OAuthUserInfo.of(OAuthProvider.from(registrationId), oAuth2UserAttributes);

        final OAuthUserJpaEntity oAuthUser = updateOrWrite(userInfo);

        return new OAuthUser(oAuthUser, oAuth2UserAttributes, userNameAttributeName);
    }

    private OAuthUserJpaEntity updateOrWrite(final OAuthUserInfo info) {
        return userRepository.findByEmail(info.email())
            .map(user -> {
                user.updateInfo(info);
                return user;
            })
            .orElseGet(() -> {
                final OAuthUserJpaEntity newUser = OAuthUserJpaEntity.builder()
                    .name(info.name())
                    .email(info.email())
                    .userRole(UserRole.ROLE_USER)
                    .build();
                return userRepository.save(newUser);
            });
    }
}
