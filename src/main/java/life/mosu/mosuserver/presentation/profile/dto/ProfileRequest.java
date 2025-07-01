package life.mosu.mosuserver.presentation.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;

import java.time.LocalDate;

public record ProfileRequest(
    @NotBlank String userName,
    @NotNull LocalDate birth,
    @NotNull Gender gender,
    @NotBlank String phoneNumber,
    String email,
    Education education,
    SchoolInfoRequest schoolInfo,
    Grade grade
) {
    public ProfileJpaEntity toEntity(Long userId) {
        return ProfileJpaEntity.builder()
            .userId(userId)
            .userName(userName)
            .birth(birth)
            .gender(gender)
            .phoneNumber(phoneNumber)
            .email(email)
            .education(education)
            .schoolInfo(schoolInfo != null ? schoolInfo.toEntity() : null)
            .grade(grade)
            .build();
    }
}