package life.mosu.mosuserver.domain.inquiry;

import life.mosu.mosuserver.presentation.inquiry.dto.InquiryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryQueryRepository {

    Page<InquiryResponse> searchInquiries(InquiryStatus status, Pageable pageable);
}
