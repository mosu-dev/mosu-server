package life.mosu.mosuserver.domain.inquiryAnswer;

import jakarta.persistence.*;
import life.mosu.mosuserver.infra.storage.domain.File;
import life.mosu.mosuserver.infra.storage.domain.Visibility;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "inquiry_answer_attachment")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class InquiryAnswerAttachmentEntity extends File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_answer_attachment_id", nullable = false)
    private Long id;

    @Column(name = "inquiry_answer_id", nullable = false)
    private Long inquiryAnswerId;

    @Builder
    public InquiryAnswerAttachmentEntity(final String fileName, final String s3Key, final Visibility visibility, final Long inquiryAnswerId) {
        super(fileName, s3Key, visibility);
        this.inquiryAnswerId = inquiryAnswerId;
    }
}
