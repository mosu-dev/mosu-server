package life.mosu.mosuserver.presentation.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;

import java.time.LocalDate;

public record EditProfileRequest(
    @NotBlank String userName,
    @NotNull LocalDate birth,
    @NotNull Gender gender,
    @NotBlank String phoneNumber,
    String email,
    Education education,
    SchoolInfoRequest schoolInfo,
    Grade grade
) {

}