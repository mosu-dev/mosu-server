package life.mosu.mosuserver.application.faq;

import java.util.List;
import life.mosu.mosuserver.domain.faq.FaqJpaEntity;
import life.mosu.mosuserver.domain.faq.FaqJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;
import life.mosu.mosuserver.presentation.faq.dto.FaqResponse;
import life.mosu.mosuserver.presentation.faq.dto.FaqUpdateRequest;
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
public class FaqService {

    private final FaqJpaRepository faqJpaRepository;
    private final FaqAttachmentService attachmentService;


    @Transactional
    public void createFaq(FaqCreateRequest request) {
        FaqJpaEntity faqEntity = faqJpaRepository.save(request.toEntity());

        attachmentService.createAttachment(request.attachments(), faqEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<FaqResponse> getFaqWithAttachments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<FaqJpaEntity> faqPage = faqJpaRepository.findAll(pageable);

        return faqPage.stream()
                .map(this::toFaqResponse)
                .toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public FaqResponse getFaqDetail(Long faqId) {
        FaqJpaEntity faq = faqJpaRepository.findById(faqId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FAQ_NOT_FOUND));

        return toFaqResponse(faq);
    }

    @Transactional
    public void update(FaqUpdateRequest request, Long faqId) {
        FaqJpaEntity faqEntity = faqJpaRepository.findById(faqId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FAQ_NOT_FOUND));

        faqEntity.update(request.question(), request.answer(), request.author());
        faqJpaRepository.save(faqEntity);

        attachmentService.deleteAttachment(faqEntity);
        attachmentService.createAttachment(request.attachments(), faqEntity);
    }


    @Transactional
    public void deleteFaq(Long faqId) {
        FaqJpaEntity faqEntity = faqJpaRepository.findById(faqId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));
        faqJpaRepository.delete(faqEntity);
        attachmentService.deleteAttachment(faqEntity);
    }


    private FaqResponse toFaqResponse(FaqJpaEntity faq) {
        return FaqResponse.of(faq, attachmentService.toAttachmentResponses(faq));
    }

}
