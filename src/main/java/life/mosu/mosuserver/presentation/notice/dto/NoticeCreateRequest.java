package life.mosu.mosuserver.presentation.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;

public record NoticeCreateRequest(

        @Schema(description = "공지사항 제목", example = "서비스 점검 안내")
        @NotNull String title,

        @Schema(description = "공지사항 본문 내용", example = "6월 30일 오전 2시부터 서비스 점검이 진행됩니다.")
        @NotNull String content,

        @Schema(description = "작성자 이름", example = "관리자")
        @NotNull String author,

        @Schema(description = "작성자 ID (추후 토큰 기반 자동 추출 예정)", example = "1")
        Long userId,

        @Schema(description = "첨부파일 리스트 (S3 key 포함)")
        List<FileRequest> attachments

) {

    public NoticeJpaEntity toEntity() {
        return NoticeJpaEntity.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .author(author)
                .build();
    }
}