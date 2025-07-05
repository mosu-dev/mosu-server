package life.mosu.mosuserver.application.faq;

import java.time.Duration;
import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqAttachmentJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqAttachmentRepository;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.infra.storage.application.AttachmentService;
import life.mosu.mosuserver.infra.storage.application.FileUploadHelper;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.presentation.faq.dto.FaqResponse;
import life.mosu.mosuserver.presentation.faq.dto.FileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqAttachmentService implements AttachmentService<FaqJpaEntity, FileRequest> {

    private final FaqAttachmentRepository faqAttachmentRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;

    @Value("${aws.s3.presigned-url-expiration-minutes}")
    private int durationTime;

    @Override
    public void createAttachment(List<FileRequest> requests, FaqJpaEntity entity) {
        if (requests == null) {
            return;
        }
        Long faqId = entity.getId();

        requests.forEach(req -> {
            fileUploadHelper.updateTag(req.s3Key());
            faqAttachmentRepository.save(req.toAttachmentEntity(
                    req.fileName(), req.s3Key(), faqId
            ));
        });
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
                                Duration.ofMinutes(durationTime)
                        )
                ))
                .toList();
    }


}
