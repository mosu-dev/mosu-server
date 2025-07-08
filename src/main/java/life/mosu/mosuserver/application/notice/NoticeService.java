package life.mosu.mosuserver.application.notice;

import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;
import life.mosu.mosuserver.domain.notice.NoticeRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.notice.dto.NoticeCreateRequest;
import life.mosu.mosuserver.presentation.notice.dto.NoticeDetailResponse;
import life.mosu.mosuserver.presentation.notice.dto.NoticeResponse;
import life.mosu.mosuserver.presentation.notice.dto.NoticeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeAttachmentService attachmentService;

    /**
     * Creates a new notice and its associated attachments.
     *
     * Persists a notice entity based on the provided request and delegates attachment creation to the attachment service.
     */
    @Transactional
    public void createNotice(NoticeCreateRequest request) {
        NoticeJpaEntity noticeEntity = noticeRepository.save(request.toEntity());
        attachmentService.createAttachment(request.attachments(), noticeEntity);
    }

    /**
     * Retrieves a paginated list of notices, each including its associated attachments.
     *
     * @param page the zero-based page index to retrieve
     * @param size the number of notices per page
     * @return a list of notice responses with their attachments
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<NoticeResponse> getNoticeWithAttachments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<NoticeJpaEntity> noticePage = noticeRepository.findAll(pageable);

        return noticePage.stream()
                .map(this::toNoticeResponse)
                .toList();
    }

    /**
     * Retrieves detailed information for a specific notice, including its attachments.
     *
     * @param noticeId the unique identifier of the notice to retrieve
     * @return a detailed response containing notice information and its attachments
     * @throws CustomRuntimeException if the notice with the given ID is not found
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public NoticeDetailResponse getNoticeDetail(Long noticeId) {
        NoticeJpaEntity notice = getNoticeOrThrow(noticeId);

        return toNoticeDetailResponse(notice);
    }

    /**
     * Deletes a notice and its associated attachments by notice ID.
     *
     * Throws a {@code CustomRuntimeException} with {@code ErrorCode.FILE_NOT_FOUND} if the notice does not exist.
     *
     * @param noticeId the ID of the notice to delete
     */
    @Transactional
    public void deleteNotice(Long noticeId) {
        NoticeJpaEntity noticeEntity = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));
        noticeRepository.delete(noticeEntity);
        attachmentService.deleteAttachment(noticeEntity);
    }

    /**
     * Updates the title, content, and attachments of an existing notice.
     *
     * Replaces the notice's current attachments with the new attachments provided in the request.
     *
     * @param noticeId the ID of the notice to update
     * @param request the update request containing new title, content, and attachments
     * @throws CustomRuntimeException if the notice is not found
     */
    @Transactional
    public void updateNotice(Long noticeId, NoticeUpdateRequest request) {
        NoticeJpaEntity noticeEntity = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));

        noticeEntity.update(request.title(), request.content());
        attachmentService.deleteAttachment(noticeEntity);
        attachmentService.createAttachment(request.attachments(), noticeEntity);
    }

    /**
     * Converts a notice entity to a response DTO, including its attachments.
     *
     * @param notice the notice entity to convert
     * @return the response DTO representing the notice and its attachments
     */
    private NoticeResponse toNoticeResponse(NoticeJpaEntity notice) {
        return NoticeResponse.of(notice, attachmentService.toAttachmentResponses(notice));
    }


    /**
     * Converts a notice entity to a detailed response DTO, including detailed attachment information.
     *
     * @param notice the notice entity to convert
     * @return a detailed response DTO representing the notice and its attachments
     */
    private NoticeDetailResponse toNoticeDetailResponse(NoticeJpaEntity notice) {
        return NoticeDetailResponse.of(
                notice,
                attachmentService.toDetailAttResponses(notice)
        );
    }

    /**
     * Retrieves a notice entity by its ID or throws a {@code CustomRuntimeException} if not found.
     *
     * @param noticeId the ID of the notice to retrieve
     * @return the {@code NoticeJpaEntity} corresponding to the given ID
     * @throws CustomRuntimeException if the notice does not exist
     */
    private NoticeJpaEntity getNoticeOrThrow(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.NOTICE_NOT_FOUND));
    }

}