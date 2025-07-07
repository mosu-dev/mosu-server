package life.mosu.mosuserver.domain.payment;

import jakarta.persistence.*;
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

    @Column(name = "payment_key", nullable = false)
    private String paymentKey;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "application_quantity", nullable = false)
    private Integer quantity;

    @Embedded
    private PaymentAmountVO paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod paymentMethod;

    @Builder(access = AccessLevel.PRIVATE)
    private PaymentJpaEntity(
            Long applicationId,
            String paymentKey,
            String orderId,
            Integer quantity,
            PaymentAmountVO paymentAmount,
            PaymentStatus paymentStatus,
            PaymentMethod paymentMethod
    ) {
        this.applicationId = applicationId;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.quantity = quantity;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

    public static PaymentJpaEntity of(
            Long applicationId,
            String paymentKey,
            String orderId,
            Integer quantity,
            PaymentStatus paymentStatus,
            PaymentAmountVO paymentAmount,
            PaymentMethod paymentMethod
    ) {
        return PaymentJpaEntity.builder()
                .applicationId(applicationId)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .quantity(quantity)
                .paymentStatus(paymentStatus)
                .paymentAmount(paymentAmount)
                .paymentMethod(paymentMethod)
                .build();
    }
}
