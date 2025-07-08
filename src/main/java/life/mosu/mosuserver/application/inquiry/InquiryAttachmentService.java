package life.mosu.mosuserver.application.inquiry;

import java.time.Duration;
import java.util.List;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentRepository;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;
import life.mosu.mosuserver.infra.property.S3Properties;
import life.mosu.mosuserver.infra.storage.FileUploadHelper;
import life.mosu.mosuserver.infra.storage.application.AttachmentService;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryAttachmentService implements AttachmentService<InquiryJpaEntity, FileRequest> {

    private final InquiryAttachmentRepository inquiryAttachmentRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;
    private final S3Properties s3Properties;

    /**
     * Saves a list of file attachments associated with the specified inquiry entity.
     *
     * Each file request is converted into an attachment entity and persisted using the repository.
     *
     * @param requests      the list of file requests to attach to the inquiry
     * @param inquiryEntity the inquiry entity to associate the attachments with
     */
    @Override
    public void createAttachment(List<FileRequest> requests, InquiryJpaEntity inquiryEntity) {
        fileUploadHelper.saveAttachments(
                requests,
                inquiryEntity.getId(),
                inquiryAttachmentRepository,
                (req, id) -> req.toInquiryAttachmentEntity(
                        req.fileName(),
                        req.s3Key(),
                        inquiryEntity.getId()
                ),
                FileRequest::s3Key
        );
    }

    /**
     * Deletes all attachments associated with the specified inquiry entity.
     *
     * @param entity the inquiry entity whose attachments should be deleted
     */
    @Override
    public void deleteAttachment(InquiryJpaEntity entity) {
        List<InquiryAttachmentJpaEntity> attachments = inquiryAttachmentRepository.findAllByInquiryId(
                entity.getId());
        inquiryAttachmentRepository.deleteAll(attachments);
    }


    public List<InquiryDetailResponse.AttachmentDetailResponse> toAttachmentResponses(
            InquiryJpaEntity inquiry) {
        List<InquiryAttachmentJpaEntity> attachments = inquiryAttachmentRepository.findAllByInquiryId(
                inquiry.getId());

        return attachments.stream()
                .map(this::createAttachDetailResponse)
                .toList();
    }

    private InquiryDetailResponse.AttachmentDetailResponse createAttachDetailResponse(
            InquiryAttachmentJpaEntity attachment) {
        String presignedUrl = s3Service.getPreSignedUrl(
                attachment.getS3Key(),
                Duration.ofMinutes(s3Properties.getPresignedUrlExpirationMinutes())
        );

        return new InquiryDetailResponse.AttachmentDetailResponse(
                attachment.getFileName(),
                presignedUrl,
                attachment.getS3Key()
        );
    }


}
