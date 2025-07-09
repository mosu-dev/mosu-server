package life.mosu.mosuserver.presentation.auth.dto;

import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.global.annotation.PasswordPattern;

public record LoginRequest(
    @NotNull String id,
    @PasswordPattern @NotNull String password
) {
}