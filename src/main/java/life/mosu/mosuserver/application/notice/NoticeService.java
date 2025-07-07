package life.mosu.mosuserver.application.notice;

import java.util.List;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;
import life.mosu.mosuserver.domain.notice.NoticeRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.notice.dto.NoticeCreateRequest;
import life.mosu.mosuserver.presentation.notice.dto.NoticeResponse;
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

    @Transactional
    public void createNotice(NoticeCreateRequest request) {
        NoticeJpaEntity noticeEntity = noticeRepository.save(request.toEntity());
        attachmentService.createAttachment(request.attachments(), noticeEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<NoticeResponse> getNoticeWithAttachments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<NoticeJpaEntity> noticePage = noticeRepository.findAll(pageable);

        return noticePage.stream()
                .map(this::toNoticeResponse)
                .toList();
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        NoticeJpaEntity noticeEntity = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));
        noticeRepository.delete(noticeEntity);
        attachmentService.deleteAttachment(noticeEntity);
    }

    private NoticeResponse toNoticeResponse(NoticeJpaEntity notice) {
        return NoticeResponse.of(notice, attachmentService.toAttachmentResponses(notice));
    }
}