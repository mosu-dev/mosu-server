package life.mosu.mosuserver.presentation.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;

@Schema(description = "프로필 수정 요청 DTO")
public record EditProfileRequest(

        @Schema(description = "사용자 이름", example = "홍길동", required = true)
        @NotBlank(message = "이름은 필수입니다.")
        String userName,

        @Schema(description = "생년월일", example = "2005-05-10", required = true)
        @NotNull(message = "생년월일은 필수입니다.")
        LocalDate birth,

        @Schema(description = "성별 (MALE 또는 FEMALE)", example = "MALE", required = true)
        @NotBlank(message = "성별은 필수입니다.")
        String gender,

        @Schema(description = "휴대폰 번호", example = "01012345678", required = true)
        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        @PhoneNumberPattern
        String phoneNumber,

        @Schema(description = "이메일 주소", example = "hong@example.com")
        String email,

        @Schema(description = "학력 정보 (Enum: ELEMENTARY, MIDDLE, HIGH_SCHOOL, UNIVERSITY 등)", example = "HIGH_SCHOOL")
        Education education,

        @Schema(description = "학교 정보", implementation = SchoolInfoRequest.class)
        SchoolInfoRequest schoolInfo,

        @Schema(description = "학년 (Enum: FIRST, SECOND, THIRD 등)", example = "SECOND")
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
