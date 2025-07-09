package life.mosu.mosuserver.presentation.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.school.Area;
import life.mosu.mosuserver.domain.school.SchoolJpaEntity;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;

@Schema(description = "신청 학교 요청 DTO")
public record ApplicationSchoolRequest(

        @Schema(description = "학교 이름", example = "대치중학교")
        @NotBlank(message = "학교 이름은 필수입니다.")
        String schoolName,

        @Schema(description = "지역 코드 (예: DAECHI, NOWON, MOKDONG)", example = "DAECHI")
        String area,

        @Schema(description = "시험 날짜", example = "2025-08-10")
        @NotNull(message = "시험 날짜는 필수입니다.")
        LocalDate examDate,

        @Schema(description = "도시락 여부 (NONE 또는 OPTION1)", example = "NONE")
        @NotBlank(message = "점심 여부는 필수입니다.")
        String lunch,

        @Schema(description = "응시 과목 목록 (예: PHYSICS_1)", example = "[\"PHYSICS_1\", \"ETHICS_AND_IDEOLOGY\"]")
        Set<String> subjects

) {

    public ApplicationSchoolJpaEntity toEntity(Long userId, Long applicationId,
            SchoolJpaEntity school) {
        return ApplicationSchoolJpaEntity.builder()
                .userId(userId)
                .applicationId(applicationId)
                .schoolId(school.getId())
                .schoolName(school.getSchoolName())
                .area(school.getArea())
                .address(school.getAddress())
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

    public Area validatedArea(String area) {
        try {
            return Area.valueOf(area.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomRuntimeException(ErrorCode.WRONG_AREA_TYPE);
        }
    }
}
