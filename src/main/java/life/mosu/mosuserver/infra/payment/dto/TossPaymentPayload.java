package life.mosu.mosuserver.infra.payment.dto;

public record TossPaymentPayload(
    String paymentKey,
    String orderId,
    Long amount
){}
