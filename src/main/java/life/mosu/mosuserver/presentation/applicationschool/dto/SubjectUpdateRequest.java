package life.mosu.mosuserver.presentation.applicationschool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import life.mosu.mosuserver.domain.application.Subject;

@Schema(description = "과목 수정 요청 DTO")
public record SubjectUpdateRequest(

        @Schema(
                description = "과목 목록 (Subject Enum 값들)",
                example = "[\"LIFE_AND_ETHICS\", \"ETHICS_AND_IDEOLOGY\"]",
                required = true
        )
        Set<Subject> subjects

) {

}
