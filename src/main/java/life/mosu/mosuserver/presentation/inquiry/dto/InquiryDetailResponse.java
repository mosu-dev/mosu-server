package life.mosu.mosuserver.presentation.inquiry.dto;

import java.util.List;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryStatus;

public record InquiryDetailResponse(
        Long id,
        String title,
        String content,
        String author,
        InquiryStatus status,
        String createdAt,
        List<AttachmentResponse> attachments
) {

    public static InquiryDetailResponse of(InquiryJpaEntity inquiry,
            List<AttachmentResponse> attachments
    ) {
        return new InquiryDetailResponse(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getContent(),
                inquiry.getAuthor(),
                inquiry.getStatus(),
                inquiry.getCreatedAt(),
                attachments
        );
    }

    public record AttachmentResponse(String fileName, String url) {

    }
}
