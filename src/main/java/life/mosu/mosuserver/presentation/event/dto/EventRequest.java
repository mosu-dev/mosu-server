package life.mosu.mosuserver.presentation.event.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import life.mosu.mosuserver.domain.event.EventJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;

public record EventRequest(
        @NotBlank
        String title,

        String eventLink,

        DurationRequest duration,

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
