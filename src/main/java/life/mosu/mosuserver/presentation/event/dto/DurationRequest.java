package life.mosu.mosuserver.presentation.event.dto;

import java.time.LocalDate;
import life.mosu.mosuserver.domain.event.DurationJpaVO;

public record DurationRequest(
        LocalDate startDate,
        LocalDate endDate
) {

    public DurationJpaVO toDurationJpaVO() {
        return new DurationJpaVO(startDate, endDate);
    }


}
