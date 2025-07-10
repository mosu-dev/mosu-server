package life.mosu.mosuserver.presentation.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.global.util.FileRequest;

public record NoticeUpdateRequest(

        @Schema(description = "공지사항 제목", example = "시스템 점검 일정 변경 안내")
        @NotNull
        String title,

        @Schema(description = "공지사항 내용", example = "점검 일정이 7월 10일로 변경되었습니다.")
        @NotNull
        String content,

        @Schema(description = "작성자 이름", example = "관리자")
        @NotNull
        String author,

        @Schema(description = "작성자 ID (토큰에서 추출 예정)", example = "42")
        Long userId,

        @Schema(description = "첨부파일 목록")
        List<FileRequest> attachments

) {

}