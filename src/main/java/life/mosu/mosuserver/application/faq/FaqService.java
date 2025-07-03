package life.mosu.mosuserver.application.faq;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import life.mosu.mosuserver.domain.faq.FaqAttachmentJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqAttachmentRepository;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.infra.storage.application.AttachmentService;
import life.mosu.mosuserver.infra.storage.application.FileUploadHelper;
import life.mosu.mosuserver.infra.storage.application.S3Service;
import life.mosu.mosuserver.infra.storage.domain.FileMoveFailLog;
import life.mosu.mosuserver.infra.storage.domain.FileMoveFailLogRepository;
import life.mosu.mosuserver.infra.storage.domain.Folder;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;
import life.mosu.mosuserver.presentation.faq.dto.FaqResponse;
import life.mosu.mosuserver.presentation.faq.dto.FileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;
    private final FaqAttachmentService attachmentService;



    @Transactional
    public void createFaq(FaqCreateRequest request) {
        FaqJpaEntity faqEntity = faqRepository.save(request.toEntity());

        attachmentService.createAttachment(request.attachments(), faqEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<FaqResponse> getFaqWithAttachments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<FaqJpaEntity> faqPage = faqRepository.findAll(pageable);

        return faqPage.stream()
            .map(this::toFaqResponse)
            .toList();
    }


    @Transactional
    public void deleteFaq(Long faqId) {
        FaqJpaEntity faqEntity = faqRepository.findById(faqId)
            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));
        faqRepository.delete(faqEntity);
        attachmentService.deleteAttachment(faqEntity);
    }


    private FaqResponse toFaqResponse(FaqJpaEntity faq) {
        return FaqResponse.of(faq, attachmentService.toAttachmentResponses(faq));
    }

}
