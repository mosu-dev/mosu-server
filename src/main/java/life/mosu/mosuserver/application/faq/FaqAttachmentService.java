package life.mosu.mosuserver.application.faq;

import java.time.Duration;
import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqAttachmentJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqAttachmentRepository;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;
import life.mosu.mosuserver.infra.property.S3Properties;
import life.mosu.mosuserver.infra.storage.FileUploadHelper;
import life.mosu.mosuserver.infra.storage.application.AttachmentService;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.presentation.faq.dto.FaqResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqAttachmentService implements AttachmentService<FaqJpaEntity, FileRequest> {

    private final FaqAttachmentRepository faqAttachmentRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;
    private final S3Properties s3Properties;


    @Override
    public void createAttachment(List<FileRequest> requests, FaqJpaEntity faqEntity) {
        fileUploadHelper.saveAttachments(
                requests,
                faqEntity.getId(),
                faqAttachmentRepository,
                (req, id) -> req.toFaqAttachmentEntity(
                        req.fileName(),
                        req.s3Key(),
                        faqEntity.getId()
                ),
                FileRequest::s3Key
        );
    }

    @Override
    public void deleteAttachment(FaqJpaEntity entity) {
        List<FaqAttachmentJpaEntity> attachments = faqAttachmentRepository.findAllByFaqId(
                entity.getId());
        faqAttachmentRepository.deleteAll(attachments);
    }


    public List<FaqResponse.AttachmentResponse> toAttachmentResponses(FaqJpaEntity faq) {

        List<FaqAttachmentJpaEntity> attachments = faqAttachmentRepository.findAllByFaqId(
                faq.getId());

        return attachments.stream()
                .map(attachment -> new FaqResponse.AttachmentResponse(
                        attachment.getFileName(),
                        s3Service.getPreSignedUrl(
                                attachment.getS3Key(),
                                Duration.ofMinutes(s3Properties.getPresignedUrlExpirationMinutes())
                        ),
                        attachment.getS3Key()
                ))
                .toList();
    }


}
