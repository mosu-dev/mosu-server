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

    /**
     * Constructs a new NoticeJpaEntity with the specified title, content, and user ID.
     *
     * @param title   the title of the notice
     * @param content the content of the notice
     * @param userId  the ID of the user associated with the notice
     */
    @Builder
    public NoticeJpaEntity(
            final String title,
            final String content,
            final Long userId
    ) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    /**
     * Updates the title and content of this notice entity.
     *
     * @param title   the new title for the notice
     * @param content the new content for the notice
     */
    public void update(
            final String title,
            final String content
    ) {
        this.title = title;
        this.content = content;
    }
}
