package life.mosu.mosuserver.application.inquiry;

import java.time.Duration;
import java.util.List;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentRepository;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.infra.storage.FileUploadHelper;
import life.mosu.mosuserver.infra.storage.application.AttachmentService;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.presentation.faq.dto.FileRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryAttachmentService implements AttachmentService<InquiryJpaEntity, FileRequest> {

    private final InquiryAttachmentRepository inquiryAttachmentRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;

    @Value("${aws.s3.presigned-url-expiration-minutes}")
    private int durationTime;

    @Override
    public void createAttachment(List<FileRequest> requests, InquiryJpaEntity inquiryEntity) {
        if (requests == null) {
            return;
        }
        Long inquiryId = inquiryEntity.getId();

        requests.forEach(req -> {
            fileUploadHelper.updateTag(req.s3Key());
            inquiryAttachmentRepository.save(req.toInquiryAttachmentEntity(
                    req.fileName(), req.s3Key(), inquiryId
            ));
        });
    }

    @Override
    public void deleteAttachment(InquiryJpaEntity entity) {
        List<InquiryAttachmentJpaEntity> attachments = inquiryAttachmentRepository.findAllByInquiryId(
                entity.getId());
        inquiryAttachmentRepository.deleteAll(attachments);
    }


    public List<InquiryDetailResponse.AttachmentResponse> toAttachmentResponses(

            InquiryJpaEntity inquiry) {

        List<InquiryAttachmentJpaEntity> attachments = inquiryAttachmentRepository.findAllByInquiryId(
                inquiry.getId());

        return attachments.stream()
                .map(attachment -> new InquiryDetailResponse.AttachmentResponse(
                        attachment.getFileName(),
                        s3Service.getPreSignedUrl(
                                attachment.getS3Key(),
                                Duration.ofMinutes(durationTime)
                        )
                ))
                .toList();
    }


}
