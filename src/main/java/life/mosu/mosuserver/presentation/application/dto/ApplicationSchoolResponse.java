package life.mosu.mosuserver.presentation.application.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;

public record ApplicationSchoolResponse(
        Long applicationSchoolId,
        String area,
        LocalDate examDate,
        String schoolName,
        String lunch,
        String examinationNumber,
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
