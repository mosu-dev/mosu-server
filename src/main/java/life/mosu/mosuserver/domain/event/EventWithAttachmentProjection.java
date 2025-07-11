package life.mosu.mosuserver.domain.event;

import java.time.LocalDate;

public record EventWithAttachmentProjection(
        Long eventId,
        String title,
        LocalDate endDate,
        String eventLink,
        AttachmentProjection attachment
) {

}