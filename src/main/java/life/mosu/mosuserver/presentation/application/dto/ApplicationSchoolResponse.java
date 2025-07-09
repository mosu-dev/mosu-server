package life.mosu.mosuserver.presentation.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;

@Schema(description = "신청 학교 응답 DTO")
public record ApplicationSchoolResponse(

        @Schema(description = "신청 학교 ID", example = "1")
        Long applicationSchoolId,

        @Schema(description = "지역 이름", example = "대치")
        String area,

        @Schema(description = "시험 날짜", example = "2025-08-10")
        LocalDate examDate,

        @Schema(description = "학교 이름", example = "대치중학교")
        String schoolName,

        @Schema(description = "도시락 신청 여부", example = "신청 안 함")
        String lunch,

        @Schema(description = "수험 번호", example = "20250001")
        String examinationNumber,

        @Schema(description = "신청 과목 목록", example = "[\"생활과 윤리\", \"정치와 법\"]")
        Set<String> subjects

) {

    public static ApplicationSchoolResponse from(ApplicationSchoolJpaEntity applicationSchool) {
        String areaName = applicationSchool.getArea().getAreaName();
        String lunchName = applicationSchool.getLunch().getLunchName();

        Set<String> subjectNames = applicationSchool.getSubjects().stream()
                .map(Subject::getSubjectName)
                .collect(Collectors.toSet());

        return new ApplicationSchoolResponse(
                applicationSchool.getId(),
                areaName,
                applicationSchool.getExamDate(),
                applicationSchool.getSchoolName(),
                lunchName,
                applicationSchool.getExaminationNumber(),
                subjectNames
        );
    }
}
