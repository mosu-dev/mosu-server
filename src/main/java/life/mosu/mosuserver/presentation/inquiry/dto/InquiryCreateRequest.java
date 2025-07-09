package life.mosu.mosuserver.presentation.inquiry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;

public record InquiryCreateRequest(
        @Schema(description = "문의 제목", example = "서비스 이용 관련 질문입니다.")
        @NotNull String title,
        @Schema(description = "문의 내용", example = "포인트는 어떻게 사용하나요?")
        @NotNull String content,
        @Schema(description = "작성자 ID 추후 토큰에서 추출하도록 변경 예정입니다.", example = "12")
        Long userId,
        @Schema(description = "작성자", example = "홍길동")
        String author,
        List<FileRequest> attachments
) {

    public InquiryJpaEntity toEntity() {
        return InquiryJpaEntity.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .author(author)
                .build();
    }
}
