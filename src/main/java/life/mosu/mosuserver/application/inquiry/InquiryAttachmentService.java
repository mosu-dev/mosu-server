package life.mosu.mosuserver.application.inquiry;

import java.time.Duration;
import java.util.List;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryAttachmentJpaRepository;
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

    private final InquiryAttachmentJpaRepository inquiryAttachmentJpaRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;
    private final S3Properties s3Properties;

    @Override
    public void createAttachment(List<FileRequest> requests, InquiryJpaEntity inquiryEntity) {
        fileUploadHelper.saveAttachments(
                requests,
                inquiryEntity.getId(),
                inquiryAttachmentJpaRepository,
                (req, id) -> req.toInquiryAttachmentEntity(
                        req.fileName(),
                        req.s3Key(),
                        inquiryEntity.getId()
                ),
                FileRequest::s3Key
        );
    }

    @Override
    public void deleteAttachment(InquiryJpaEntity entity) {
        List<InquiryAttachmentJpaEntity> attachments = inquiryAttachmentJpaRepository.findAllByInquiryId(
                entity.getId());
        inquiryAttachmentJpaRepository.deleteAll(attachments);
    }


    public List<InquiryDetailResponse.AttachmentDetailResponse> toAttachmentResponses(
            InquiryJpaEntity inquiry) {
        List<InquiryAttachmentJpaEntity> attachments = inquiryAttachmentJpaRepository.findAllByInquiryId(
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
