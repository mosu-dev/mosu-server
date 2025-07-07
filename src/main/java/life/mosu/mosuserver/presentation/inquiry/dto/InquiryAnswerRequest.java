package life.mosu.mosuserver.presentation.inquiry.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;

public record InquiryAnswerRequest(
        @NotNull String title,
        @NotNull String content,
        Long userId,
        List<FileRequest> attachments
) {

    public InquiryAnswerJpaEntity toEntity(Long postId) {
        return InquiryAnswerJpaEntity.builder()
                .inquiryId(postId)
                .title(title)
                .content(content)
                .userId(userId)
                .build();
    }
}