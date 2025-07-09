package life.mosu.mosuserver.presentation.applicationschool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import life.mosu.mosuserver.domain.refund.RefundJpaEntity;

@Schema(description = "환불 요청 DTO")
public record RefundRequest(

        @Schema(description = "환불 사유", example = "개인 사정으로 인해 응시가 어려움")
        String reason,

        @Schema(description = "환불 정책 동의 여부", example = "true")
        Boolean refundAgreed,

        @Schema(description = "환불 동의 시각 (ISO 8601 형식)", example = "2025-07-10T15:30:00")
        LocalDateTime agreedAt

) {

    public RefundJpaEntity toEntity(Long applicationSchoolId) {
        return RefundJpaEntity.builder()
                .applicationSchoolId(applicationSchoolId)
                .reason(reason)
                .refundAgreed(refundAgreed)
                .agreedAt(agreedAt)
                .build();
    }
}
