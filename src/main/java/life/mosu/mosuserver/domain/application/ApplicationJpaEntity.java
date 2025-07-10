package life.mosu.mosuserver.domain.application;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "guardian_phone_number")
    private String guardianPhoneNumber;

    @Column(name = "agreed_to_notices")
    private Boolean agreedToNotices;

    @Column(name = "agreed_to_refund_policy")
    private Boolean agreedToRefundPolicy;

    @Builder
    public ApplicationJpaEntity(
            final Long userId,
            final String guardianPhoneNumber,
            final boolean agreedToNotices,
            final boolean agreedToRefundPolicy

    ) {
        this.userId = userId;
        this.guardianPhoneNumber = guardianPhoneNumber;
        this.agreedToNotices = agreedToNotices;
        this.agreedToRefundPolicy = agreedToRefundPolicy;
    }

}