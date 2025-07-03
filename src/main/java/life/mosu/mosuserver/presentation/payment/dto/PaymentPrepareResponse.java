package life.mosu.mosuserver.presentation.payment.dto;

import java.math.BigDecimal;

public record PaymentPrepareResponse(String orderId, BigDecimal totalPrice) {
    public static PaymentPrepareResponse of(String orderId, BigDecimal totalPrice) {
        return new PaymentPrepareResponse(orderId, totalPrice);
    }
}
