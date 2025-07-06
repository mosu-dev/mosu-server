package life.mosu.mosuserver.presentation.payment.dto;

import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.infra.payment.dto.TossPaymentPayload;

public record PaymentRequest(
        @NotNull Long applicationId,
        @NotNull String paymentKey,
        @NotNull String orderId,
        @NotNull Long amount
) {
    public TossPaymentPayload toPayload() {
        return new TossPaymentPayload(paymentKey, orderId, amount);
    }
}