package life.mosu.mosuserver.presentation.auth.dto;

import static life.mosu.mosuserver.global.util.EncodeUtil.passwordEncode;

import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.domain.user.UserRole;
import life.mosu.mosuserver.global.annotation.PasswordPattern;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignUpRequest(
        @NotNull String id,
        @PasswordPattern @NotNull String password
) {

    public UserJpaEntity toEntity(PasswordEncoder passwordEncoder) {
        return UserJpaEntity.builder()
                .loginId(id)
                .password(passwordEncode(passwordEncoder, password))
                .userRole(UserRole.ROLE_PENDING)
                .build();
    }
}