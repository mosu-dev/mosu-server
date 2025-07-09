package life.mosu.mosuserver.presentation.inquiry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import life.mosu.mosuserver.global.util.FileRequest;

public record InquiryAnswerUpdateRequest(
        @Schema(description = "문의 제목", example = "서비스 이용 관련 질문입니다.")
        String title,
        @Schema(description = "문의 내용", example = "포인트는 어떻게 사용하나요?")
        String content,
        List<FileRequest> attachments
) {

}
