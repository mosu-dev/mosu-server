package life.mosu.mosuserver.application.inquiry;

import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryRepository;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

//    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
//    public List<InquiryResponse> getInquiries(InquiryStatus status, Pageable pageable) {
//        List<InquiryJpaEntity> inquiries = inquiryRepository.findByOptionalStatus(status, pageable);
//        return inquiries.stream()
//                .map(this::toInquiryResponse)
//                .toList();
//    }
//
//    private InquiryResponse toInquiryResponse(InquiryJpaEntity inquiry) {
//        return InquiryResponse.of(inquiry);
//    }
//
//    private InquiryDetailResponse toInquiryDetailResponse(InquiryJpaEntity inquiry) {
//        return InquiryDetailResponse.of(inquiry,
//                inquiryAttachmentService.toAttachmentResponses(inquiry));
//    }

}
