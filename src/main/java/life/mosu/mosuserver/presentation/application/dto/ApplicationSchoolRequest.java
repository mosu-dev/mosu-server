package life.mosu.mosuserver.presentation.application.dto;

import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.school.Area;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record ApplicationSchoolRequest(
    Long schoolId,
    String schoolName,
    String area,
    AddressRequest address,
    LocalDate examDate,
    String lunch,
    Set<String> subjects
) {

    public ApplicationSchoolJpaEntity toEntity(Long userId, Long applicationId) {
        return ApplicationSchoolJpaEntity.builder()
            .userId(userId)
            .applicationId(applicationId)
            .schoolId(schoolId)
            .schoolName(schoolName)
            .area(validatedArea(area))
            .address(address.toValueObject())
            .examDate(examDate)
            .lunch(validatedLunch(lunch))
            .subjects(validatedSubjects(subjects))
            .build();
    }

    private Set<Subject> validatedSubjects(Set<String> subjects) {
        try {
            return subjects.stream()
                .map(Subject::valueOf)
                .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new CustomRuntimeException(ErrorCode.WRONG_SUBJECT_TYPE);
        }
    }

    private Lunch validatedLunch(String lunch) {
        try {
            return Lunch.valueOf(lunch.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomRuntimeException(ErrorCode.WRONG_LUNCH_TYPE);
        }
    }

    private Area validatedArea(String area) {
        try {
            return Area.valueOf(area.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomRuntimeException(ErrorCode.WRONG_AREA_TYPE);
        }
    }
}
