package life.mosu.mosuserver.presentation.faq.dto;

import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;

public record FaqResponse(
    Long id,
    String title,
    String content,
    List<AttachmentResponse> attachments
) {
    public static FaqResponse of(FaqJpaEntity faq, List<AttachmentResponse> attachments) {
        return new FaqResponse(
            faq.getId(),
            faq.getQuestion(),
            faq.getAnswer(),
            attachments
        );
    }

    public record AttachmentResponse(String fileName, String url) {}
}