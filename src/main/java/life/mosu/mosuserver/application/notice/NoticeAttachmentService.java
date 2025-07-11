package life.mosu.mosuserver.application.notice;

import java.time.Duration;
import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeAttachmentJpaEntity;
import life.mosu.mosuserver.domain.notice.NoticeAttachmentJpaRepository;
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

    private final NoticeAttachmentJpaRepository noticeAttachmentJpaRepository;
    private final FileUploadHelper fileUploadHelper;
    private final S3Service s3Service;
    private final S3Properties s3Properties;

    @Override
    public void createAttachment(List<FileRequest> requests, NoticeJpaEntity noticeEntity) {
        fileUploadHelper.saveAttachments(
                requests,
                noticeEntity.getId(),
                noticeAttachmentJpaRepository,
                (req, id) -> req.toNoticeAttachmentEntity(
                        req.fileName(),
                        req.s3Key(),
                        noticeEntity.getId()
                ),
                FileRequest::s3Key
        );
    }

    @Override
    public void deleteAttachment(NoticeJpaEntity entity) {
        List<NoticeAttachmentJpaEntity> attachments = noticeAttachmentJpaRepository.findAllByNoticeId(
                entity.getId());
        noticeAttachmentJpaRepository.deleteAll(attachments);
    }

    public List<NoticeResponse.AttachmentResponse> toAttachmentResponses(NoticeJpaEntity notice) {

        List<NoticeAttachmentJpaEntity> attachments = noticeAttachmentJpaRepository.findAllByNoticeId(
                notice.getId());

        return attachments.stream()
                .map(attachment -> new NoticeResponse.AttachmentResponse(
                        attachment.getFileName(),
                        fileUrl(attachment.getS3Key())
                ))
                .toList();
    }

    public List<NoticeDetailResponse.AttachmentDetailResponse> toDetailAttResponses(
            NoticeJpaEntity notice) {

        List<NoticeAttachmentJpaEntity> attachments = noticeAttachmentJpaRepository.findAllByNoticeId(
                notice.getId());

        return attachments.stream()
                .map(attachment -> new NoticeDetailResponse.AttachmentDetailResponse(
                        attachment.getFileName(),
                        fileUrl(attachment.getS3Key()),
                        attachment.getS3Key()

                ))
                .toList();
    }

    private String fileUrl(String s3Key) {
        return s3Service.getPreSignedUrl(
                s3Key,
                Duration.ofMinutes(s3Properties.getPresignedUrlExpirationMinutes())
        );
    }
}

