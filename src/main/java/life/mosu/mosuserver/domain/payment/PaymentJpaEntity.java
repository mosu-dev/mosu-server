package life.mosu.mosuserver.domain.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Embedded
    private PaymentAmountVO paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private PaymentMethod paymentMethod;

    @Builder(access = AccessLevel.PRIVATE)
    private PaymentJpaEntity(
            Long applicationId,
            String paymentKey,
            String orderId,
            PaymentAmountVO paymentAmount,
            PaymentStatus paymentStatus,
            PaymentMethod paymentMethod
    ) {
        this.applicationId = applicationId;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

    public static PaymentJpaEntity of(
            Long applicationId,
            String paymentKey,
            String orderId,
            PaymentStatus paymentStatus,
            PaymentAmountVO paymentAmount,
            PaymentMethod paymentMethod
    ) {
        return PaymentJpaEntity.builder()
                .applicationId(applicationId)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .paymentStatus(paymentStatus)
                .paymentAmount(paymentAmount)
                .paymentMethod(paymentMethod)
                .build();
    }

    public static PaymentJpaEntity ofFailure(
            Long applicationId,
            String orderId,
            PaymentStatus paymentStatus,
            Integer totalAmount
    ) {
        PaymentAmountVO paymentAmount = PaymentAmountVO.ofFailure(totalAmount);
        return PaymentJpaEntity.builder()
                .applicationId(applicationId)
                .orderId(orderId)
                .paymentStatus(paymentStatus)
                .paymentAmount(paymentAmount)
                .build();
    }

    public void changeStatus(PaymentStatus status) {
        this.paymentStatus = status;
    }
}
