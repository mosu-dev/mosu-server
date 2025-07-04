package life.mosu.mosuserver.presentation.application.dto;

import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.school.Area;

import java.time.LocalDate;
import java.util.Set;

public record ApplicationSchoolRequest(
    Long schoolId,
    String schoolName,
    Area area,
    AddressRequest address,
    LocalDate examDate,
    Lunch lunch,
    Set<Subject> subjects,
    Integer amount
) {

    public ApplicationSchoolJpaEntity toEntity(Long applicationId) {
        return ApplicationSchoolJpaEntity.builder()
            .applicationId(applicationId)
            .schoolId(schoolId)
            .schoolName(schoolName)
            .area(area)
            .address(address.toValueObject())
            .examDate(examDate)
            .lunch(lunch)
            .subjects(subjects)
            .amount(amount)
            .build();
    }
}
