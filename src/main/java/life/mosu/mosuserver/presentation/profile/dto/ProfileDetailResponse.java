package life.mosu.mosuserver.presentation.profile.dto;

import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;

import java.time.LocalDate;

public record ProfileDetailResponse(
    Long profileId,
    String userName,
    LocalDate birth,
    Gender gender,
    String phoneNumber,
    String email,
    Education education,
    SchoolInfoResponse schoolInfo,
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
