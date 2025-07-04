package life.mosu.mosuserver.domain.application;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "application")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "recommender_phone_number")
    private String recommenderPhoneNumber;

    @Column(name = "agreed_to_notices")
    private boolean agreedToNotices;

    @Column(name = "agreed_to_refund_policy")
    private boolean agreedToRefundPolicy;

    @Column(name = "agreed_to_notices_at")
    private LocalDate agreedToNoticesAt;

    @Column(name = "agreed_to_refund_policy_at")
    private LocalDate agreedToRefundPolicyAt;

    @Column(name = "amount")
    private Integer amount;

    @Builder
    public ApplicationJpaEntity(
        final Long userId,
        final String recommenderPhoneNumber,
        final boolean agreedToNotices,
        final boolean agreedToRefundPolicy,
        final LocalDate agreedToNoticesAt,
        final LocalDate agreedToRefundPolicyAt,
        final Integer amount

    ) {
        this.userId = userId;
        this.recommenderPhoneNumber = recommenderPhoneNumber;
        this.agreedToNotices = agreedToNotices;
        this.agreedToRefundPolicy = agreedToRefundPolicy;
        this.agreedToNoticesAt = agreedToNoticesAt;
        this.agreedToRefundPolicyAt = agreedToRefundPolicyAt;
        this.amount = amount;
    }

}
/**
 * application school ---1:n---- subject ( applicationSchoolId  INDEX)
 * (CollectionTable vs Separate Entity)
 * <p>
 * select * from subject where application_id = ?;
 * select * from
 * CollectionTable
 */
