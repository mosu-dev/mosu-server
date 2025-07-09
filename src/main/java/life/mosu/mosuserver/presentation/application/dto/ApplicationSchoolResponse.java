package life.mosu.mosuserver.presentation.application.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.school.Area;

public record ApplicationSchoolResponse(
        Long applicationSchoolId,
        Area area,
        LocalDate examDate,
        String schoolName,
        Lunch lunch,
        String examinationNumber,
        Set<String> subjects
) {

    public static ApplicationSchoolResponse from(ApplicationSchoolJpaEntity applicationSchool) {
        Set<String> subjectNames = applicationSchool.getSubjects().stream()
                .map(Subject::getSubjectName)
                .collect(Collectors.toSet());

        return new ApplicationSchoolResponse(
                applicationSchool.getId(),
                applicationSchool.getArea(),
                applicationSchool.getExamDate(),
                applicationSchool.getSchoolName(),
                applicationSchool.getLunch(),
                applicationSchool.getExaminationNumber(),
                subjectNames
        );
    }


}
