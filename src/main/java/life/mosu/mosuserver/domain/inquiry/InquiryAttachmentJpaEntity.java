package life.mosu.mosuserver.domain.inquiry;

import jakarta.persistence.*;
import life.mosu.mosuserver.infra.storage.domain.File;
import life.mosu.mosuserver.infra.storage.domain.Visibility;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "inquiry_attachment")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class InquiryAttachmentJpaEntity extends File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_attachment_id", nullable = false)
    private Long id;

    @Column(name = "inquiry_id", nullable = false)
    private Long inquiryId;

    @Builder
    public InquiryAttachmentJpaEntity(final String fileName, final String s3Key, final Visibility visibility, final Long inquiryId) {
        super(fileName, s3Key, visibility);
        this.inquiryId = inquiryId;
    }
}