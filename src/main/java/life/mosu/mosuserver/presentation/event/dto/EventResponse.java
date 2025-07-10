package life.mosu.mosuserver.presentation.event.dto;

import java.time.LocalDate;
import life.mosu.mosuserver.domain.event.EventWithAttachmentProjection;

public record EventResponse(
        Long eventId,
        String title,
        LocalDate endDate,
        String eventLink,
        AttachmentResponse attachment
) {

    public static EventResponse of(EventWithAttachmentProjection event, String eventUrl) {
        AttachmentResponse attachment = new AttachmentResponse(event.attachment().fileName(),
                eventUrl, event.attachment().s3Key());

        return new EventResponse(
                event.eventId(),
                event.title(),
                event.endDate(),
                event.eventLink(),
                attachment
        );
    }

    public record AttachmentResponse(
            String fileName,
            String url,
            String s3Key
    ) {

    }
}
