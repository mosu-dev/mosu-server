package life.mosu.mosuserver.domain.notice;

import jakarta.persistence.*;
import life.mosu.mosuserver.infra.storage.domain.File;
import life.mosu.mosuserver.infra.storage.domain.Visibility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "notice_attachment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeAttachmentJpaEntity extends File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_attachment_id", nullable = false)
    private Long id;

    @Column(name = "notice_id", nullable = false)
    private Long noticeId;

    @Builder
    public NoticeAttachmentJpaEntity(final String fileName, final String s3Key, final Visibility visibility, final Long noticeId) {
        super(fileName, s3Key, visibility);
        this.noticeId = noticeId;
    }
}
