package life.mosu.mosuserver.application.inquiry;

import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryRepository;
import life.mosu.mosuserver.domain.inquiry.InquiryStatus;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryCreateRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse;
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

    private final InquiryRepository inquiryRepository;
    private final InquiryAttachmentService inquiryAttachmentService;

    @Transactional
    public void createInquiry(InquiryCreateRequest request) {
        InquiryJpaEntity inquiryEntity = inquiryRepository.save(request.toEntity());
        inquiryAttachmentService.createAttachment(request.attachments(), inquiryEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<InquiryResponse> getInquiries(
            InquiryStatus status,
            String sortField,
            boolean asc,
            Pageable pageable) {

        return inquiryRepository.searchInquiries(status, sortField, asc, pageable);

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public InquiryDetailResponse getInquiryDetail(Long postId) {
        InquiryJpaEntity inquiry = inquiryRepository.findById(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));

        return toInquiryDetailResponse(inquiry);
    }

    @Transactional
    public void delete(Long postId) {
        InquiryJpaEntity inquiryEntity = inquiryRepository.findById(postId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.FILE_NOT_FOUND));

        inquiryRepository.delete(inquiryEntity);
        inquiryAttachmentService.deleteAttachment(inquiryEntity);
    }

    private InquiryResponse toInquiryResponse(InquiryJpaEntity inquiry) {
        return InquiryResponse.of(inquiry);
    }

    private InquiryDetailResponse toInquiryDetailResponse(InquiryJpaEntity inquiry) {
        return InquiryDetailResponse.of(inquiry,
                inquiryAttachmentService.toAttachmentResponses(inquiry));
    }

}
