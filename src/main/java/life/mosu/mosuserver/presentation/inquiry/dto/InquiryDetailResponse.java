package life.mosu.mosuserver.presentation.inquiry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaEntity;

@Schema(description = "1:1 문의 응답 DTO")
public record InquiryDetailResponse(
        @Schema(description = "문의 ID", example = "1")
        Long id,
        @Schema(description = "문의 제목", example = "서비스 이용 관련 질문입니다.")
        String title,
        @Schema(description = "문의 내용", example = "포인트는 어떻게 사용하나요?")
        String content,
        @Schema(description = "작성자", example = "홍길동")
        String author,
        @Schema(description = "문의 상태 (미응답, 완료)", example = "완료")
        String status,
        @Schema(description = "문의 등록일", example = "2025-07-10")
        String createdAt,

        List<AttachmentDetailResponse> attachments,

        InquiryAnswerDetailResponse answer
) {

    public static InquiryDetailResponse of(
            InquiryJpaEntity inquiry,
            List<AttachmentDetailResponse> attachments,
            InquiryAnswerDetailResponse answer
    ) {
        return new InquiryDetailResponse(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getContent(),
                inquiry.getAuthor(),
                inquiry.getStatus().getStatusName(),
                inquiry.getCreatedAt(),
                attachments,
                answer
        );
    }

    public record AttachmentResponse(
            @Schema(description = "파일 이름", example = "image.png")
            String fileName,
            @Schema(description = "파일 URL", example = "https://example.com/image.png")
            String url) {

    }

    public record AttachmentDetailResponse(
            @Schema(description = "파일 이름", example = "image.png")
            String fileName,
            @Schema(description = "파일 URL", example = "https://example.com/image.png")
            String url,
            @Schema(description = "S3 키", example = "비공개 이미지를 처리하기 위한 키")
            String s3Key) {

    }

    public record InquiryAnswerDetailResponse(
            @Schema(description = "문의 ID", example = "1")
            Long id,
            @Schema(description = "문의 제목", example = "서비스 이용 관련 질문입니다.")
            String title,
            @Schema(description = "문의 내용", example = "포인트는 어떻게 사용하나요?")
            String content,
            @Schema(description = "작성일", example = "2025-07-10T10:00:00")
            String createdAt,
            List<AttachmentDetailResponse> attachments
    ) {

        public static InquiryAnswerDetailResponse of(InquiryAnswerJpaEntity answer,
                List<AttachmentDetailResponse> attachments) {
            return new InquiryAnswerDetailResponse(
                    answer.getId(),
                    answer.getTitle(),
                    answer.getContent(),
                    answer.getCreatedAt(),
                    attachments
            );
        }

    }


}
