package life.mosu.mosuserver.presentation.payment.dto;

import life.mosu.mosuserver.infra.payment.application.dto.TossPaymentPayload;

public record PaymentRequest(
        Long applicationId,
        String paymentKey,
        String orderId,
        Long amount
) {
    public TossPaymentPayload toPayload(){
        return new TossPaymentPayload(paymentKey, orderId, amount);
    }
}