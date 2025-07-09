package life.mosu.mosuserver.presentation.notice.dto;

import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;

public record NoticeDetailResponse(
        Long id,
        String title,
        String content,
        String author,
        String createdAt,
        List<AttachmentDetailResponse> attachments
) {

    public static NoticeDetailResponse of(
            NoticeJpaEntity notice,
            List<AttachmentDetailResponse> attachments
    ) {
        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getAuthor(),
                notice.getCreatedAt(),
                attachments
        );
    }

    public record AttachmentDetailResponse(
            String fileName,
            String url,
            String s3Key
    ) {

    }
}