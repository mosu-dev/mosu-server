package life.mosu.mosuserver.presentation.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.school.AddressJpaVO;
import life.mosu.mosuserver.domain.school.Area;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;

public record ApplicationSchoolRequest(
        @NotNull(message = "학교 ID는 필수입니다.")
        Long schoolId,

        @NotBlank(message = "학교 이름은 필수입니다.")
        String schoolName,

        String area,

        @NotNull(message = "시험 날짜는 필수입니다.")
        LocalDate examDate,

        @NotBlank(message = "점심 여부는 필수입니다.")
        String lunch,

        Set<String> subjects
) {

    public ApplicationSchoolJpaEntity toEntity(Long userId, Long applicationId,
            AddressJpaVO address) {
        return ApplicationSchoolJpaEntity.builder()
                .userId(userId)
                .applicationId(applicationId)
                .schoolId(schoolId)
                .schoolName(schoolName)
                .area(validatedArea(area))
                .address(address)
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
