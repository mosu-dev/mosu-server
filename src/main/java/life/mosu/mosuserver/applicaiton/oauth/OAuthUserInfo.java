package life.mosu.mosuserver.applicaiton.oauth;

import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuthUserInfo(
    String name,
    String email,
    String profile
) {

    public static OAuthUserInfo of(
        final String registrationId,
        final Map<String, Object> attributes
    ) {
        return switch (registrationId) {
            case "kakao" -> ofKakao(attributes);
            default -> throw new CustomRuntimeException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        };
    }

    private static OAuthUserInfo ofKakao(final Map<String, Object> attributes) {
        final Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        final Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuthUserInfo.builder()
            .name((String) profile.get("name"))
            .email("hello@gmail.com")
            .build();
    }
}