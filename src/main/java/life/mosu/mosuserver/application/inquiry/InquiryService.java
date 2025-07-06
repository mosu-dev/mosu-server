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


}
