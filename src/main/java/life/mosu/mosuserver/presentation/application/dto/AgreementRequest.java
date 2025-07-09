package life.mosu.mosuserver.presentation.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "약관 동의 요청 DTO")
public record AgreementRequest(

        @Schema(description = "공지사항 확인 및 동의 여부", example = "true")
        boolean agreedToNotices,

        @Schema(description = "환불 정책 동의 여부", example = "true")
        boolean agreedToRefundPolicy

) {

}
