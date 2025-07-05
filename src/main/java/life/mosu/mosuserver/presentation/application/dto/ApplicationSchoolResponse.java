package life.mosu.mosuserver.presentation.application.dto;

import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.school.Area;

import java.time.LocalDate;
import java.util.Set;

public record ApplicationSchoolResponse(
    Long applicationSchoolId,
    Area area,
    LocalDate examDate,
    String schoolName,
    Lunch lunch,
    String examinationNumber,
    Set<Subject> subjects
) {

    public static ApplicationSchoolResponse from(ApplicationSchoolJpaEntity applicationSchool) {
        return new ApplicationSchoolResponse(
            applicationSchool.getId(),
            applicationSchool.getArea(),
            applicationSchool.getExamDate(),
            applicationSchool.getSchoolName(),
            applicationSchool.getLunch(),
            applicationSchool.getExaminationNumber(),
            applicationSchool.getSubjects()
        );
    }


}
