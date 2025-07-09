package life.mosu.mosuserver.presentation.school.dto;

import java.time.LocalDate;
import life.mosu.mosuserver.domain.school.SchoolJpaEntity;
import life.mosu.mosuserver.presentation.common.AddressResponse;

public record SchoolResponse(
        Long id,
        String schoolName,
        String area,
        AddressResponse address,
        LocalDate examDate,
        Long capacity
) {

    public static SchoolResponse from(SchoolJpaEntity school) {
        return new SchoolResponse(
                school.getId(),
                school.getSchoolName(),
                school.getArea().name(),
                AddressResponse.from(school.getAddress()),
                school.getExamDate(),
                school.getCapacity()
        );
    }
}
