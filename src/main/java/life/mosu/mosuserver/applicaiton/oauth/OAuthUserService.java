package life.mosu.mosuserver.applicaiton.oauth;

import life.mosu.mosuserver.domain.user.OAuthUserJpaEntity;
import life.mosu.mosuserver.domain.user.OAuthUserJpaRepository;
import life.mosu.mosuserver.domain.user.UserJpaRepository;
import life.mosu.mosuserver.domain.user.UserRole;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
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

        final OAuthUserInfo userInfo = OAuthUserInfo.of(registrationId, oAuth2UserAttributes);

        final OAuthUserJpaEntity oAuthUser = updateOrWrite(userInfo);

        return new OAuthUser(oAuthUser, oAuth2UserAttributes, userNameAttributeName);
    }

    private OAuthUserJpaEntity updateOrWrite(
        final OAuthUserInfo info
    ) {
        if (userRepository.existsByEmail(info.email())) {
            final OAuthUserJpaEntity user = userRepository.findByEmail(info.email())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.OAUTH_USER_ALREADY_EXISTS));
            user.updateInfo(info);
            return user;
        }
        final OAuthUserJpaEntity user = OAuthUserJpaEntity.builder()
            .name(info.name())
            .email(info.email())
            .userRole(UserRole.ROLE_USER)
            .build();
        return userRepository.save(user);
    }
}
