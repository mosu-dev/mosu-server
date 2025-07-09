package life.mosu.mosuserver.presentation.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;

@Schema(description = "프로필 상세 응답 DTO")
public record ProfileDetailResponse(

        @Schema(description = "프로필 ID", example = "1")
        Long profileId,

        @Schema(description = "사용자 이름", example = "홍길동")
        String userName,

        @Schema(description = "생년월일", example = "2005-05-10")
        LocalDate birth,

        @Schema(description = "성별", example = "MALE")
        Gender gender,

        @Schema(description = "휴대폰 번호", example = "010-1234-5678")
        String phoneNumber,

        @Schema(description = "이메일 주소", example = "hong@example.com")
        String email,

        @Schema(description = "학력 (예: ENROLLED, GRADUATED)", example = "ENROLLED")
        Education education,

        @Schema(description = "학교 정보")
        SchoolInfoResponse schoolInfo,

        @Schema(description = "학년", example = "HIGH_1")
        Grade grade

) {

    public static ProfileDetailResponse from(ProfileJpaEntity profile) {
        return new ProfileDetailResponse(
                profile.getId(),
                profile.getUserName(),
                profile.getBirth(),
                profile.getGender(),
                profile.getPhoneNumber(),
                profile.getEmail(),
                profile.getEducation(),
                SchoolInfoResponse.from(profile.getSchoolInfo()),
                profile.getGrade()
        );
    }
}
