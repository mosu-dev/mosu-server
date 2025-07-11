package life.mosu.mosuserver.application.inquiry;

import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaRepository;
import life.mosu.mosuserver.domain.inquiry.InquiryStatus;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryCreateRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse.InquiryAnswerDetailResponse;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryAttachmentService inquiryAttachmentService;
    private final InquiryJpaRepository inquiryJpaRepository;
    private final InquiryAnswerService inquiryAnswerService;
    private final InquiryAnswerJpaRepository inquiryAnswerJpaRepository;

    @Transactional
    public void createInquiry(InquiryCreateRequest request) {
        InquiryJpaEntity inquiryEntity = inquiryJpaRepository.save(request.toEntity());
        inquiryAttachmentService.createAttachment(request.attachments(), inquiryEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<InquiryResponse> getInquiries(
            InquiryStatus status,
            String sortField,
            boolean asc,
            Pageable pageable) {

        return inquiryJpaRepository.searchInquiries(status, sortField, asc, pageable);

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public InquiryDetailResponse getInquiryDetail(Long postId) {
        InquiryJpaEntity inquiry = getInquiryOrThrow(postId);

        return toInquiryDetailResponse(inquiry);
    }

    @Transactional
    public void deleteInquiry(Long postId) {
        InquiryJpaEntity inquiryEntity = getInquiryOrThrow(postId);

        inquiryAnswerJpaRepository.findByInquiryId(postId).ifPresent(answer -> {
            inquiryAnswerService.deleteInquiryAnswer(postId);
        });

        inquiryAttachmentService.deleteAttachment(inquiryEntity);

        inquiryJpaRepository.delete(inquiryEntity);
    }


    private InquiryResponse toInquiryResponse(InquiryJpaEntity inquiry) {
        return InquiryResponse.of(inquiry);
    }

    private InquiryDetailResponse toInquiryDetailResponse(InquiryJpaEntity inquiry) {
        InquiryAnswerDetailResponse answer = inquiryAnswerService.getInquiryAnswerDetail(
                inquiry.getId());

        return InquiryDetailResponse.of(inquiry,
                inquiryAttachmentService.toAttachmentResponses(inquiry), answer);
    }

    private InquiryJpaEntity getInquiryOrThrow(Long postId) {
        return inquiryJpaRepository.findById(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.INQUIRY_NOT_FOUND));
    }

}
