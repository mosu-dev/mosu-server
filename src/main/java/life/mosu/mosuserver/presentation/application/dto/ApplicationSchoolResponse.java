package life.mosu.mosuserver.presentation.application.dto;

import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.school.Area;
import life.mosu.mosuserver.domain.subject.Subject;

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
    //    public static ApplicationSchoolResponse of(ApplicationSchoolJpaEntity applicationSchool, Set<Subject> subjects) {
//        return new ApplicationSchoolResponse(
//            applicationSchool.getId(),
//            applicationSchool.getArea(),
//            applicationSchool.getExamDate(),
//            applicationSchool.getSchoolName(),
//            applicationSchool.getLunch(),
//            applicationSchool.getExaminationNumber(),
//            subjects
//        );
//    }
    public static ApplicationSchoolResponse of(ApplicationSchoolJpaEntity applicationSchool, Set<Subject> subjects) {
        return new ApplicationSchoolResponse(
            applicationSchool.getId(),
            applicationSchool.getArea(),
            applicationSchool.getExamDate(),
            applicationSchool.getSchoolName(),
            applicationSchool.getLunch(),
            applicationSchool.getExaminationNumber(),
            subjects
        );
    }
}