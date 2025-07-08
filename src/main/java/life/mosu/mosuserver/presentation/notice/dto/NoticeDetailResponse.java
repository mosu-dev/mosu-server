package life.mosu.mosuserver.presentation.notice.dto;

import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;

public record NoticeDetailResponse(
        Long id,
        String title,
        String content,
        List<AttachmentDetailResponse> attachments
) {

    /**
     * Creates a NoticeDetailResponse from a NoticeJpaEntity and a list of attachment details.
     *
     * @param notice the notice entity containing the ID, title, and content
     * @param attachments the list of attachment details to associate with the notice
     * @return a NoticeDetailResponse representing the notice and its attachments
     */
    public static NoticeDetailResponse of(
            NoticeJpaEntity notice,
            List<AttachmentDetailResponse> attachments
    ) {
        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
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