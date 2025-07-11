package life.mosu.mosuserver.presentation.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.event.EventWithAttachmentProjection;

@Schema(description = "이벤트 응답 DTO")
public record EventResponse(

        @Schema(description = "이벤트 ID", example = "1")
        Long eventId,

        @Schema(description = "이벤트 제목", example = "여름방학 이벤트")
        String title,

        @Schema(description = "이벤트 종료일", example = "2025-07-31")
        LocalDate endDate,

        @Schema(description = "이벤트 링크 URL", example = "https://mosu.life/event/summer")
        String eventLink,

        @Schema(description = "이벤트 첨부파일 정보")
        AttachmentResponse attachment

) {

    public static EventResponse of(EventWithAttachmentProjection event, String eventUrl) {
        AttachmentResponse attachment = new AttachmentResponse(
                event.attachment().fileName(),
                eventUrl,
                event.attachment().s3Key()
        );

        return new EventResponse(
                event.eventId(),
                event.title(),
                event.endDate(),
                event.eventLink(),
                attachment
        );
    }

    @Schema(description = "이벤트 첨부파일 DTO")
    public record AttachmentResponse(
            @Schema(description = "파일명", example = "event-banner.png")
            String fileName,

            @Schema(description = "파일 접근 URL", example = "https://your-bucket.s3.amazonaws.com/event/2025/banner.png")
            String url,

            @Schema(description = "S3 키", example = "event/2025/banner.png")
            String s3Key
    ) {

    }
}
