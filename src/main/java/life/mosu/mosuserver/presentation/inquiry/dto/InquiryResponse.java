package life.mosu.mosuserver.presentation.inquiry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryStatus;

@Schema(description = "1:1 문의 응답 DTO")
public record InquiryResponse(
        @Schema(description = "문의 ID", example = "1")
        Long id,

        @Schema(description = "문의 제목", example = "서비스 이용 관련 질문입니다.")
        String title,

        @Schema(description = "문의 내용", example = "포인트는 어떻게 사용하나요?")
        String content,

        @Schema(description = "작성자", example = "홍길동")
        String author,

        @Schema(description = "문의 상태 (PENDING: 답변 대기, COMPLETED: 답변 완료)", example = "PENDING")
        InquiryStatus status,

        @Schema(description = "문의 등록일", example = "2025-07-10")
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