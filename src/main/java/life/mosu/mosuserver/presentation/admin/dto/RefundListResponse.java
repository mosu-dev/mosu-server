package life.mosu.mosuserver.presentation.admin.dto;

import life.mosu.mosuserver.domain.payment.PaymentMethod;

public record RefundListResponse(
        Long refundId,
        String examNumber,
        String name,
        String phone,
        String requestedAt,
        String completedAt,
        PaymentMethod paymentMethod,
        String reason

) {

}
