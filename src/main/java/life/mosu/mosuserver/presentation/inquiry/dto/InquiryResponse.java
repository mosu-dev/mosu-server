package life.mosu.mosuserver.presentation.inquiry.dto;

import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryStatus;

public record InquiryResponse(
        Long id,
        String title,
        String content,
        String author,
        InquiryStatus status,
        String createdAt

) {

    public static InquiryResponse of(InquiryJpaEntity inquiry) {
        return new InquiryResponse(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getContent(),
                inquiry.getAuthor(),
                inquiry.getStatus(),
                inquiry.getCreatedAt()
        );
    }
}
