package life.mosu.mosuserver.presentation.notice.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;

public record NoticeCreateRequest(

        @NotNull String title,
        @NotNull String content,
        Long userId,
        List<FileRequest> attachments

) {

    public NoticeJpaEntity toEntity() {
        return NoticeJpaEntity.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();
    }
}
