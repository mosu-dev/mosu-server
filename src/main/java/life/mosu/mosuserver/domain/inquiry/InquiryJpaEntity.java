package life.mosu.mosuserver.domain.inquiry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import life.mosu.mosuserver.domain.base.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "inquiry")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class InquiryJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String author;

    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    @Builder
    public InquiryJpaEntity(final String title, final String content, final Long userId,
            final String author) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.author = author;
        this.status = InquiryStatus.PENDING;
    }

    public void updateStatusToComplete() {
        this.status = InquiryStatus.COMPLETED;
    }

    public void updateStatusToPending() {
        this.status = InquiryStatus.PENDING;
    }
}
