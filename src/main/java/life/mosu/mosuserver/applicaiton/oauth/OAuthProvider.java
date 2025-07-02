package life.mosu.mosuserver.applicaiton.oauth;

import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;

import java.util.Arrays;

public enum OAuthProvider {
    KAKAO("kakao");

    private final String registrationId;

    private OAuthProvider(String registrationId) {
        this.registrationId = registrationId;
    }

    public static OAuthProvider from(String registrationId) {
        return Arrays.stream(values())
            .filter(provider -> provider.registrationId.equals(registrationId))
            .findFirst()
            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER));
    }
}