package life.mosu.mosuserver.infra.payment.application.dto;

public record TossPaymentPayload(
    String paymentKey,
    String orderId,
    Long amount
){}
