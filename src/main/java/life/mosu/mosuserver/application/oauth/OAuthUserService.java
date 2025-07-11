package life.mosu.mosuserver.application.oauth;

import java.time.LocalDate;
import java.util.Map;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.domain.user.UserJpaRepository;
import life.mosu.mosuserver.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

    private final UserJpaRepository userRepository;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        final OAuth2User user = super.loadUser(userRequest);

        final Map<String, Object> oAuth2UserAttributes = user.getAttributes();
        System.out.println("OAuth2User Attributes: " + oAuth2UserAttributes.toString());
        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        final OAuthUserInfo userInfo = OAuthUserInfo.of(OAuthProvider.from(registrationId),
                oAuth2UserAttributes);

        final UserJpaEntity oAuthUser = updateOrWrite(userInfo);

        return new OAuthUser(oAuthUser, oAuth2UserAttributes, userNameAttributeName);
    }

    private UserJpaEntity updateOrWrite(final OAuthUserInfo info) {

        return userRepository.findByLoginId(info.email())
                .map(existingUser -> {
                    existingUser.updateOAuthUser(info.gender(), info.name(),
                            info.birthDay() != null ? info.birthDay() : LocalDate.of(1900, 1, 1));
                    return existingUser;
                })
                .orElseGet(() -> {
                    final UserJpaEntity newUser = UserJpaEntity.builder()
                            .loginId(info.email())
                            .gender(info.gender() != null ? info.gender() : Gender.MALE)
                            .name(info.name())
                            .birth(info.birthDay() != null ? info.birthDay()
                                    : LocalDate.of(1900, 1, 1))
                            .userRole(UserRole.ROLE_PENDING)
                            .agreedToTermsOfService(true)
                            .agreedToPrivacyPolicy(true)
                            .agreedToMarketing(false)
                            .build();

                    return userRepository.save(newUser);
                });
    }
}
