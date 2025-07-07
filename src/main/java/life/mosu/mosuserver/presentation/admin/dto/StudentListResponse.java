package life.mosu.mosuserver.presentation.admin.dto;

import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Grade;

public record StudentListResponse(
    String name,
    String birthDate,
    String phoneNumber,
    String gender,
    Education educationLevel,
    String schoolName,
    Grade grade,
    int examCount
) {}