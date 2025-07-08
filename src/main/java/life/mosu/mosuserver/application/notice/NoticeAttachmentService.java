package life.mosu.mosuserver.application.notice;

import java.time.Duration;
import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeAttachmentJpaEntity;
import life.mosu.mosuserver.domain.notice.NoticeAttachmentRepository;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;
import life.mosu.mosuserver.global.util.FileRequest;
import life.mosu.mosuserver.infra.property.S3Properties;
import life.mosu.mosuserver.infra.storage.FileUploadHelper;
import life.mosu.mosuserver.infra.storage.application.AttachmentService;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.presentation.notice.dto.NoticeDetailResponse;
import life.mosu.mosuserver.presentation.notice.dto.NoticeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeAttachmentService implements AttachmentService<NoticeJpaEntity, FileRequest> {

    private final NoticeAttachmentRepository noticeAttachmentRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;
    private final S3Properties s3Properties;

    /**
     * Saves a list of file attachments associated with the specified notice entity.
     *
     * Each file request is converted into a notice attachment entity and persisted using the attachment repository.
     *
     * @param requests      the list of file requests to be attached to the notice
     * @param noticeEntity  the notice entity to which the attachments will be linked
     */
    @Override
    public void createAttachment(List<FileRequest> requests, NoticeJpaEntity noticeEntity) {
        fileUploadHelper.saveAttachments(
                requests,
                noticeEntity.getId(),
                noticeAttachmentRepository,
                (req, id) -> req.toNoticeAttachmentEntity(
                        req.fileName(),
                        req.s3Key(),
                        noticeEntity.getId()
                ),
                FileRequest::s3Key
        );
    }

    /**
     * Deletes all attachments associated with the specified notice entity.
     *
     * @param entity the notice entity whose attachments will be deleted
     */
    @Override
    public void deleteAttachment(NoticeJpaEntity entity) {
        List<NoticeAttachmentJpaEntity> attachments = noticeAttachmentRepository.findAllByNoticeId(
                entity.getId());
        noticeAttachmentRepository.deleteAll(attachments);
    }

    /**
     * Converts all attachments of the specified notice entity into a list of response DTOs,
     * each containing the file name and a pre-signed URL for secure access.
     *
     * @param notice the notice entity whose attachments are to be converted
     * @return a list of attachment response DTOs with file names and pre-signed URLs
     */
    public List<NoticeResponse.AttachmentResponse> toAttachmentResponses(NoticeJpaEntity notice) {

        List<NoticeAttachmentJpaEntity> attachments = noticeAttachmentRepository.findAllByNoticeId(
                notice.getId());

        return attachments.stream()
                .map(attachment -> new NoticeResponse.AttachmentResponse(
                        attachment.getFileName(),
                        s3Service.getPreSignedUrl(
                                attachment.getS3Key(),
                                Duration.ofMinutes(s3Properties.getPresignedUrlExpirationMinutes())
                        )
                ))
                .toList();
    }

    /**
     * Converts all attachments associated with the given notice entity into a list of detailed attachment response DTOs.
     *
     * Each response includes the file name, a pre-signed URL for accessing the file, and the S3 key.
     *
     * @param notice the notice entity whose attachments are to be converted
     * @return a list of detailed attachment response DTOs for the notice
     */
    public List<NoticeDetailResponse.AttachmentDetailResponse> toDetailAttResponses(
            NoticeJpaEntity notice) {

        List<NoticeAttachmentJpaEntity> attachments = noticeAttachmentRepository.findAllByNoticeId(
                notice.getId());

        return attachments.stream()
                .map(attachment -> new NoticeDetailResponse.AttachmentDetailResponse(
                        attachment.getFileName(),
                        fileUrl(attachment.getS3Key()),
                        attachment.getS3Key()

                ))
                .toList();
    }

    /**
     * Generates a pre-signed URL for the specified S3 key with an expiration time defined in the S3 properties.
     *
     * @param s3Key the S3 object key for which to generate the pre-signed URL
     * @return a pre-signed URL granting temporary access to the S3 object
     */
    private String fileUrl(String s3Key) {
        return s3Service.getPreSignedUrl(
                s3Key,
                Duration.ofMinutes(s3Properties.getPresignedUrlExpirationMinutes())
        );
    }
}

