package life.mosu.mosuserver.domain.inquiry;

import jakarta.persistence.*;
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

    @Builder
    public InquiryJpaEntity(final String title, final String content, final Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
