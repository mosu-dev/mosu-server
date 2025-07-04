package life.mosu.mosuserver.presentation.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;

import java.time.LocalDate;

public record EditProfileRequest(
    @NotBlank(message = "이름은 필수입니다.")
    String userName,

    @NotNull(message = "생년월일은 필수입니다.")
    LocalDate birth,

    @NotBlank(message = "성별은 필수입니다.")
    String gender,

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    @Pattern(
        regexp = "^01[016789]\\d{7,8}$",
        message = "휴대폰 번호 형식이 올바르지 않습니다. 예: 01012345678"
    )
    String phoneNumber,

    String email,
    Education education,
    SchoolInfoRequest schoolInfo,
    Grade grade
) {

    public Gender validatedGender() {
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CustomRuntimeException(ErrorCode.INVALID_GENDER);
        }
    }
}