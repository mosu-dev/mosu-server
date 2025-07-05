package life.mosu.mosuserver.presentation.applicationschool.dto;

import life.mosu.mosuserver.domain.refund.RefundJpaEntity;

import java.time.LocalDateTime;

public record RefundRequest(
    String reason,
    Boolean refundAgreed,
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
