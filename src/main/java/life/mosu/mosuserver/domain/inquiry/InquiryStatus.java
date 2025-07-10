package life.mosu.mosuserver.domain.inquiry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryStatus {
    PENDING("미응답"),
    COMPLETED("완료");
    
    private final String statusName;
}
