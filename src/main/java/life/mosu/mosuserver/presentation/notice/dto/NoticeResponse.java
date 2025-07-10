package life.mosu.mosuserver.presentation.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;

public record NoticeResponse(

        @Schema(description = "공지사항 ID", example = "1")
        Long id,

        @Schema(description = "공지사항 제목", example = "서비스 점검 안내")
        String title,

        @Schema(description = "공지사항 내용", example = "6월 30일 오전 2시부터 서비스 점검이 진행됩니다.")
        String content,

        @Schema(description = "작성자 이름", example = "관리자")
        String author,

        @Schema(description = "작성일시 (yyyy-MM-dd)", example = "2025-07-08")
        String createdAt,

        @Schema(description = "첨부파일 목록")
        List<AttachmentResponse> attachments
) {

    public static NoticeResponse of(NoticeJpaEntity notice, List<AttachmentResponse> attachments) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getAuthor(),
                notice.getCreatedAt(),
                attachments
        );
    }

    public record AttachmentResponse(

            @Schema(description = "파일 이름", example = "service_guide.pdf")
            String fileName,

            @Schema(description = "S3 Presigned URL", example = "https://bucket.s3.amazonaws.com/.../service_guide.pdf")
            String url
    ) {

    }
}