package life.mosu.mosuserver.presentation.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import life.mosu.mosuserver.domain.event.EventJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;

@Schema(description = "이벤트 등록/수정 요청 DTO")
public record EventRequest(

        @NotBlank
        @Schema(description = "이벤트 제목", example = "여름방학 이벤트")
        String title,

        @Schema(description = "이벤트 링크 URL", example = "https://mosu.life/event/summer")
        String eventLink,

        @Schema(description = "이벤트 기간")
        DurationRequest duration,

        @Schema(description = "이벤트 첨부파일 (선택)")
        FileRequest attachment

) {

    public List<FileRequest> optionalAttachment() {
        FileRequest parsedAttachment = this.attachment;
        return parsedAttachment == null ? List.of() : List.of(parsedAttachment);
    }

    public EventJpaEntity toEntity() {
        return EventJpaEntity.builder()
                .title(title)
                .eventLink(eventLink)
                .duration(duration.toDurationJpaVO())
                .build();
    }
}
