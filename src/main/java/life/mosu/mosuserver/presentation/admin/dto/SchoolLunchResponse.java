package life.mosu.mosuserver.presentation.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "학교별 도시락 신청 수 응답 DTO")
public record SchoolLunchResponse(

        @Schema(description = "학교 이름", example = "서울고등학교")
        String schoolName,

        @Schema(description = "도시락 신청 수", example = "42")
        Long count

) {

}
