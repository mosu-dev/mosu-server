package life.mosu.mosuserver.infra.payment.dto;

import life.mosu.mosuserver.domain.payment.PaymentAmountVO;
import life.mosu.mosuserver.domain.payment.PaymentJpaEntity;
import life.mosu.mosuserver.domain.payment.PaymentMethod;
import life.mosu.mosuserver.domain.payment.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class ConfirmTossPaymentResponse {

    private String paymentKey;
    private String orderId;
    private String status;
    private String approvedAt;
    private Integer totalAmount;
    private Integer balanceAmount;
    private Integer suppliedAmount;
    private Integer vat;
    private Integer taxFreeAmount;
    private String method;

    public PaymentJpaEntity toEntity(Long applicationId) {
        return PaymentJpaEntity.of(
                applicationId,
                paymentKey,
                orderId,
                toPaymentStatus(),
                toPaymentAmount(),
                toPaymentMethod());
    }

    private PaymentMethod toPaymentMethod() {
        return PaymentMethod.from(method);
    }

    private PaymentStatus toPaymentStatus() {
        return PaymentStatus.from(status);
    }

    private PaymentAmountVO toPaymentAmount() {
        return PaymentAmountVO.of(
                totalAmount,
                suppliedAmount,
                vat,
                balanceAmount,
                taxFreeAmount);
    }
}

