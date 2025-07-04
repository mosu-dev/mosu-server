package life.mosu.mosuserver.presentation.application.dto;

import java.util.List;

public record ApplicationResponse(
    Long applicationId,
    List<ApplicationSchoolResponse> schools,
    Integer amount
) {

//    public static ApplicationResponse of(
//        Long applicationId,
//        List<ApplicationSchoolJpaEntity> schoolEntities,
//        Set<Subject> subjects,
//        Integer amount
//    ) {
//        List<ApplicationSchoolResponse> schoolResponses = schoolEntities.stream()
//            .map(applicationSchoolJpa -> ApplicationSchoolResponse.of(applicationSchoolJpa, subjects))
//            .toList();
//
//        return new ApplicationResponse(applicationId, schoolResponses, amount);
//    }

    public static ApplicationResponse of(
        Long applicationId,
        List<ApplicationSchoolResponse> schoolEntities,
        Integer amount
    ) {
        return new ApplicationResponse(applicationId, schoolEntities, amount);
    }

}
