package life.mosu.mosuserver.presentation.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.event.DurationJpaVO;

@Schema(description = "이벤트 기간 요청 DTO")
public record DurationRequest(

        @Schema(description = "이벤트 시작일", example = "2025-07-01")
        LocalDate startDate,

        @Schema(description = "이벤트 종료일", example = "2025-07-31")
        LocalDate endDate

) {

    public DurationJpaVO toDurationJpaVO() {
        return new DurationJpaVO(startDate, endDate);
    }
}
