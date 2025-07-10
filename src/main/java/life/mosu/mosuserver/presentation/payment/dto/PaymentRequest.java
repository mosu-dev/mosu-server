package life.mosu.mosuserver.presentation.payment.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import life.mosu.mosuserver.infra.payment.dto.TossPaymentPayload;

public record PaymentRequest(
        @NotNull List<Long> applicationSchoolIds,
        @NotNull String paymentKey,
        @NotNull String orderId,
        @NotNull Integer amount
) {

    public TossPaymentPayload toPayload() {
        return new TossPaymentPayload(paymentKey, orderId, amount);
    }

    public int applicantSize() {
        return applicationSchoolIds.size();
    }
}