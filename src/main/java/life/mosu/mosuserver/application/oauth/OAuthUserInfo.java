package life.mosu.mosuserver.application.oauth;

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
        final OAuthProvider oAuthProvider,
        final Map<String, Object> attributes
    ) {
        return switch (oAuthProvider) {
            case KAKAO -> ofKakao(attributes);
            default -> throw new CustomRuntimeException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        };
    }

    private static OAuthUserInfo ofKakao(final Map<String, Object> attributes) {

        final Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = null;
        String email = null;

        if (account != null) {
            profile = (Map<String, Object>) account.get("profile");
            email = (String) account.get("email");
        }

        if (profile != null) {
            String name = (String) profile.get("name");

            return OAuthUserInfo.builder()
                .name(name)
                .email("test123@gmali.com")
                .build();
        } else {
            throw new CustomRuntimeException(ErrorCode.FAILED_TO_GET_KAKAO_OAUTH_USER);
        }
    }
}