package life.mosu.mosuserver.presentation.faq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;

public record FaqResponse(
        @Schema(description = "FAQ ID") Long id,
        @Schema(description = "질문") String question,
        @Schema(description = "답변") String answer,
        @Schema(description = "작성 일자 (yyyy-MM-dd)") String createdAt,
        @Schema(description = "첨부파일 리스트") List<AttachmentResponse> attachments
) {

    public static FaqResponse of(FaqJpaEntity faq, List<AttachmentResponse> attachments) {
        return new FaqResponse(
                faq.getId(),
                faq.getQuestion(),
                faq.getAnswer(),
                faq.getCreatedAt().substring(0, 10), // 일자만 반환
                attachments
        );
    }

    public record AttachmentResponse(
            @Schema(description = "파일명") String fileName,
            @Schema(description = "파일 URL") String url,
            @Schema(description = "S3 키") String s3Key
    ) {

    }
}