package life.mosu.mosuserver.domain.faq;

import jakarta.persistence.*;
import life.mosu.mosuserver.infra.storage.domain.File;
import life.mosu.mosuserver.infra.storage.domain.Visibility;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

@Getter
@Entity
@Table(name = "faq_attachment")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@SoftDelete
public class FaqAttachmentJpaEntity extends File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_attachment_id", nullable = false)
    private Long id;

    @Column(name = "faq_id", nullable = false)
    private Long faqId;

    @Builder
    public FaqAttachmentJpaEntity(final String fileName, final String s3Key, final Visibility visibility, final Long faqId) {
        super(fileName, s3Key, visibility);
        this.faqId = faqId;
    }
}
