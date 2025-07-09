package life.mosu.mosuserver.application.oauth;

import java.time.LocalDate;
import java.util.Map;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import lombok.Builder;

@Builder
public record OAuthUserInfo(
        String email,
        String name,
        Gender gender,
        LocalDate birthDay
) {

    public static OAuthUserInfo of(
            final OAuthProvider oAuthProvider,
            final Map<String, Object> attributes
    ) {
        return switch (oAuthProvider) {
            case OAuthProvider.KAKAO -> ofKakao(attributes);
            default -> throw new CustomRuntimeException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        };
    }

    private static OAuthUserInfo ofKakao(final Map<String, Object> attributes) {

        final Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = null;
        String email = null;
        String gender = null;
        String birthDay = null;
        String birthYear = null;

        if (account != null) {
            profile = (Map<String, Object>) account.get("profile");
            gender = (String) account.get("gender");
            birthDay = (String) account.get("birthDay");
            birthYear = (String) account.get("birthYear");
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