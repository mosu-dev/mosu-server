package life.mosu.mosuserver.domain.payment;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int id;

    @Column(name = "application_id")
    private Long applicationId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Builder
    public PaymentJpaEntity(
        final Long applicationId,
        final PaymentStatus paymentStatus,
        final PaymentMethod paymentMethod
    ) {
        this.applicationId = applicationId;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }


}
