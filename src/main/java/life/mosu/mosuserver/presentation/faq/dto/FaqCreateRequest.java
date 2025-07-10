package life.mosu.mosuserver.presentation.faq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;

public record FaqCreateRequest(

        @Schema(description = "FAQ 질문", example = "서비스 이용에 대해 궁금합니다.")
        @NotNull String question,

        @Schema(description = "FAQ 답변", example = "서비스는 로그인 후 사용 가능합니다.")
        @NotNull String answer,

        @Schema(description = "작성자 이름", example = "관리자")
        @NotNull String author,

        @Schema(description = "작성자 ID (추후 토큰에서 추출 예정)", example = "1")
        Long userId,

        @Schema(description = "첨부파일 리스트")
        List<FileRequest> attachments

) {

    public FaqJpaEntity toEntity() {
        return FaqJpaEntity.builder()
                .question(question)
                .answer(answer)
                .author(author)
                .userId(userId)
                .build();
    }
}