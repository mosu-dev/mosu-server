package life.mosu.mosuserver.presentation.inquiry.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.presentation.faq.dto.FileRequest;

public record InquiryCreateRequest(
        @NotNull String title,
        @NotNull String content,
        Long userId,
        List<FileRequest> attachments
) {

    public InquiryJpaEntity toEntity() {
        return InquiryJpaEntity.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();
    }
}
