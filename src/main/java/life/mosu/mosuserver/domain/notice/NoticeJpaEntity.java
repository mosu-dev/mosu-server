package life.mosu.mosuserver.domain.notice;

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
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 3000)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "author", nullable = false)
    private String author;

    @Builder
    public NoticeJpaEntity(
            final String title,
            final String content,
            final Long userId,
            final String author
    ) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.author = author;
    }

    public void update(
            final String title,
            final String content,
            final String author
    ) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
