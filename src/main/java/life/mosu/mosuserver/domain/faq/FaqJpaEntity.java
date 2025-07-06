package life.mosu.mosuserver.domain.faq;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "faq")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class FaqJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id", nullable = false)
    private Long id;

    @Column(name = "question", nullable = false, length = 500)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public FaqJpaEntity(final String question, final String answer, final Long userId) {
        this.question = question;
        this.answer = answer;
        this.userId = userId;
    }
}
