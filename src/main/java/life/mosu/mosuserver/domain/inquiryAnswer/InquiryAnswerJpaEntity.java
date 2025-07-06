package life.mosu.mosuserver.domain.inquiryAnswer;

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

@Getter
@Entity
@Table(name = "inquiry_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryAnswerJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_answer_id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "inquiry_id", nullable = false)
    private Long inquiryId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public InquiryAnswerJpaEntity(
            final String content,
            final Long inquiryId,
            final Long userId
    ) {
        this.content = content;
        this.inquiryId = inquiryId;
        this.userId = userId;
    }
}
