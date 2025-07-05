package life.mosu.mosuserver.domain.refund;

import jakarta.persistence.*;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "refund")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private Long id;

    @Column(name = "application_school_id")
    private Long applicationSchoolId;

    @Column(name = "refund_reason", nullable = false)
    private String reason;

    @Column(name = "refund_agreed")
    private boolean refundAgreed;

    @Column(name = "agreed_at")
    private LocalDateTime agreedAt;

    @Builder
    public RefundJpaEntity(
        final Long applicationSchoolId,
        final String reason,
        final Boolean refundAgreed,
        final LocalDateTime agreedAt
    ) {
        this.applicationSchoolId = applicationSchoolId;
        this.reason = reason;
        this.refundAgreed = refundAgreed;
        this.agreedAt = agreedAt;
    }

}
