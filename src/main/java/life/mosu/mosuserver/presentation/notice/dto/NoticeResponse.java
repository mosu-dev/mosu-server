package life.mosu.mosuserver.presentation.notice.dto;

import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;

public record NoticeResponse(
        Long id,
        String title,
        String content,
        String author,
        List<AttachmentResponse> attachments
) {

    public static NoticeResponse of(NoticeJpaEntity notice, List<AttachmentResponse> attachments) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getAuthor(),
                attachments
        );
    }

    public record AttachmentResponse(String fileName, String url) {

    }
}