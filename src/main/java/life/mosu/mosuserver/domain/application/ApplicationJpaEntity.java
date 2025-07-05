package life.mosu.mosuserver.domain.application;

import jakarta.persistence.*;
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

    @Column(name = "recommender_phone_number")
    private String recommenderPhoneNumber;

    @Column(name = "agreed_to_notices")
    private Boolean agreedToNotices;

    @Column(name = "agreed_to_refund_policy")
    private Boolean agreedToRefundPolicy;


    @Builder
    public ApplicationJpaEntity(
        final Long userId,
        final String recommenderPhoneNumber,
        final boolean agreedToNotices,
        final boolean agreedToRefundPolicy

    ) {
        this.userId = userId;
        this.recommenderPhoneNumber = recommenderPhoneNumber;
        this.agreedToNotices = agreedToNotices;
        this.agreedToRefundPolicy = agreedToRefundPolicy;
    }

}
