package life.mosu.mosuserver.presentation.application.dto;

import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;

import java.util.List;

public record ApplicationResponse(
    Long applicationId,
    List<ApplicationSchoolResponse> schools
) {

    public static ApplicationResponse of(
        Long applicationId,
        List<ApplicationSchoolJpaEntity> schoolEntities
    ) {
        List<ApplicationSchoolResponse> schoolResponses = schoolEntities.stream()
            .map(ApplicationSchoolResponse::from)
            .toList();

        return new ApplicationResponse(applicationId, schoolResponses);
    }

}
