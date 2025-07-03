package life.mosu.mosuserver.presentation.payment.dto;

public record PaymentPrepareResponse(String orderId, int totalPrice) {
    public static PaymentPrepareResponse of(String orderId, int totalAmount) {
        return new PaymentPrepareResponse(orderId, totalAmount);
    }
}
