package life.mosu.mosuserver.application.inquiry;

import java.time.Duration;
import java.util.List;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerAttachmentEntity;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerAttachmentRepository;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaEntity;
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
public class InquiryAnswerAttachmentService implements
        AttachmentService<InquiryAnswerJpaEntity, FileRequest> {

    private final S3Properties s3Properties;
    private final InquiryAnswerAttachmentRepository attachmentRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;

    @Override
    public void createAttachment(List<FileRequest> requests, InquiryAnswerJpaEntity answerEntity) {
        fileUploadHelper.saveAttachments(
                requests,
                answerEntity.getId(),
                attachmentRepository,
                (req, id) -> req.toInquiryAnswerAttachmentEntity(
                        req.fileName(),
                        req.s3Key(),
                        answerEntity.getId()
                ),
                FileRequest::s3Key
        );
    }


    @Override
    public void deleteAttachment(InquiryAnswerJpaEntity entity) {
        List<InquiryAnswerAttachmentEntity> attachments = attachmentRepository.findAllByInquiryAnswerId(
                entity.getId());

        attachmentRepository.deleteAll(attachments);
    }


    public List<InquiryDetailResponse.AttachmentDetailResponse> toAttachmentResponses(
            InquiryAnswerJpaEntity inquiry) {

        List<InquiryAnswerAttachmentEntity> attachments = attachmentRepository.findAllByInquiryAnswerId(
                inquiry.getId());

        return attachments.stream()
                .map(this::createAttachDetailResponse)
                .toList();
    }

    private InquiryDetailResponse.AttachmentResponse createAttachResponse(
            InquiryAnswerAttachmentEntity attachment) {
        String presignedUrl = s3Service.getPreSignedUrl(
                attachment.getS3Key(),
                Duration.ofMinutes(s3Properties.getPresignedUrlExpirationMinutes())
        );

        return new InquiryDetailResponse.AttachmentResponse(
                attachment.getFileName(),
                presignedUrl
        );
    }

    private InquiryDetailResponse.AttachmentDetailResponse createAttachDetailResponse(
            InquiryAnswerAttachmentEntity attachment) {
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