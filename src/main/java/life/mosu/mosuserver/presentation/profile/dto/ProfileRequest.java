package life.mosu.mosuserver.presentation.profile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;
import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;

public record ProfileRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String userName,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,

        @NotBlank(message = "성별은 필수입니다.")
        String gender,

        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        @PhoneNumberPattern
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

    public ProfileJpaEntity toEntity(Long userId) {
        return ProfileJpaEntity.builder()
                .userId(userId)
                .userName(userName)
                .birth(birth)
                .gender(validatedGender())
                .phoneNumber(phoneNumber)
                .email(email)
                .education(education)
                .schoolInfo(schoolInfo != null ? schoolInfo.toEntity() : null)
                .grade(grade)
                .build();
    }
}
