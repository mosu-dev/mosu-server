package life.mosu.mosuserver.presentation.profile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "프로필 등록 요청 DTO")
public record ProfileRequest(

        @Schema(description = "사용자 이름", example = "홍길동", required = true)
        @NotBlank(message = "이름은 필수입니다.")
        String userName,

        @Schema(description = "생년월일 (yyyy-MM-dd 형식)", example = "2005-05-10", required = true)
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,

        @Schema(description = "성별 (MALE 또는 FEMALE)", example = "MALE", required = true)
        @NotBlank(message = "성별은 필수입니다.")
        String gender,

        @Schema(description = "휴대폰 번호", example = "010-1234-5678", required = true)
        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        @PhoneNumberPattern
        String phoneNumber,

        @Schema(description = "이메일 주소", example = "hong@example.com")
        String email,

        @Schema(description = "학력 (Enum: ENROLLED, GRADUATED 등)", example = "HIGH_1")
        Education education,

        @Schema(description = "학교 정보", implementation = SchoolInfoRequest.class)
        SchoolInfoRequest schoolInfo,

        @Schema(description = "학년 (Enum: HIGH_1, HIGH_2, HIGH_3 등)", example = "HIGH_1")
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
