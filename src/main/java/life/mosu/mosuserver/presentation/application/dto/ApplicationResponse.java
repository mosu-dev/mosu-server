package life.mosu.mosuserver.presentation.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;

@Schema(description = "신청 응답 DTO")
public record ApplicationResponse(

        @Schema(description = "신청 ID", example = "1")
        Long applicationId,

        @Schema(description = "신청 학교 목록")
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
