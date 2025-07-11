package life.mosu.mosuserver.application.notice;

import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;
import life.mosu.mosuserver.domain.notice.NoticeJpaRepository;
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

    private final NoticeJpaRepository noticeJpaRepository;
    private final NoticeAttachmentService attachmentService;

    @Transactional
    public void createNotice(NoticeCreateRequest request) {
        NoticeJpaEntity noticeEntity = noticeJpaRepository.save(request.toEntity());
        attachmentService.createAttachment(request.attachments(), noticeEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<NoticeResponse> getNoticeWithAttachments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<NoticeJpaEntity> noticePage = noticeJpaRepository.findAll(pageable);

        return noticePage.stream()
                .map(this::toNoticeResponse)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public NoticeDetailResponse getNoticeDetail(Long noticeId) {
        NoticeJpaEntity notice = getNoticeOrThrow(noticeId);

        return toNoticeDetailResponse(notice);
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        NoticeJpaEntity noticeEntity = getNoticeOrThrow(noticeId);
        noticeJpaRepository.delete(noticeEntity);
        attachmentService.deleteAttachment(noticeEntity);
    }

    @Transactional
    public void updateNotice(Long noticeId, NoticeUpdateRequest request) {
        NoticeJpaEntity noticeEntity = getNoticeOrThrow(noticeId);

        noticeEntity.update(request.title(), request.content(), request.author());
        attachmentService.deleteAttachment(noticeEntity);
        attachmentService.createAttachment(request.attachments(), noticeEntity);
    }

    private NoticeResponse toNoticeResponse(NoticeJpaEntity notice) {
        return NoticeResponse.of(notice, attachmentService.toAttachmentResponses(notice));
    }


    private NoticeDetailResponse toNoticeDetailResponse(NoticeJpaEntity notice) {
        return NoticeDetailResponse.of(
                notice,
                attachmentService.toDetailAttResponses(notice)
        );
    }

    private NoticeJpaEntity getNoticeOrThrow(Long noticeId) {
        return noticeJpaRepository.findById(noticeId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.NOTICE_NOT_FOUND));
    }

}