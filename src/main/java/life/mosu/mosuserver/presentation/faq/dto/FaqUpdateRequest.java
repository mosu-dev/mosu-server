package life.mosu.mosuserver.presentation.faq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.global.util.FileRequest;

public record FaqUpdateRequest(

        @Schema(description = "수정할 질문 내용", example = "서비스 사용에 대해 알고 싶습니다.")
        @NotNull String question,

        @Schema(description = "수정할 답변 내용", example = "로그인 후 전체 메뉴가 노출됩니다.")
        @NotNull String answer,

        @Schema(description = "작성자 이름", example = "관리자")
        @NotNull String author,

        @Schema(description = "첨부파일 리스트")
        List<FileRequest> attachments

) {

}