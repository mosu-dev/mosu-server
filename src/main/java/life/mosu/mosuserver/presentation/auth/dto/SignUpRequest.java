package life.mosu.mosuserver.presentation.auth.dto;

import static life.mosu.mosuserver.global.util.EncodeUtil.passwordEncode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.domain.user.UserRole;
import life.mosu.mosuserver.global.annotation.PasswordPattern;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignUpRequest(
        @Schema(
                description = "로그인 ID",
                example = "mosu12370"
        )
        @NotNull String id,
        @Schema(
                description = "비밀번호는 8~20자의 영문 대/소문자, 숫자, 특수문자를 모두 포함해야 합니다.",
                example = "Mosu!1234"
        )
        @PasswordPattern @NotNull String password,
        SignUpServiceTermRequest serviceTermRequest
) {

    public UserJpaEntity toAuthEntity(PasswordEncoder passwordEncoder) {
        return UserJpaEntity.builder()
                .loginId(id)
                .password(passwordEncode(passwordEncoder, password))
                .agreedToTermsOfService(serviceTermRequest.agreedToTermsOfService())
                .agreedToPrivacyPolicy(serviceTermRequest.agreedToPrivacyPolicy())
                .agreedToMarketing(serviceTermRequest.agreedToMarketing())
                .gender(Gender.PENDING)
                .userRole(UserRole.ROLE_PENDING)
                .build();
    }
}