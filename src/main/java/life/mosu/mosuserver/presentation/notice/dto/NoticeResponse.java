package life.mosu.mosuserver.presentation.notice.dto;

import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;

public record NoticeResponse(
        Long id,
        String title,
        String content,
        List<AttachmentResponse> attachments
) {

    /**
     * Creates a NoticeResponse from a NoticeJpaEntity and a list of attachments.
     *
     * @param notice the notice entity containing the id, title, and content
     * @param attachments the list of attachments to associate with the notice
     * @return a NoticeResponse representing the notice and its attachments
     */
    public static NoticeResponse of(NoticeJpaEntity notice, List<AttachmentResponse> attachments) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                attachments
        );
    }

    public record AttachmentResponse(String fileName, String url) {

    }
}