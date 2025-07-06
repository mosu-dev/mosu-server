package life.mosu.mosuserver.presentation.inquiry;

import life.mosu.mosuserver.application.inquiry.InquiryService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Void>> create(
            @RequestBody InquiryCreateRequest request) {
        inquiryService.createInquiry(request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.CREATED, "질문 등록 성공"));
    }

//    @GetMapping("/list")
//    public ResponseEntity<List<InquiryResponse>> getInquiryList() {
//        List<InquiryResponse> inquiries = inquiryService;
//        return ResponseEntity.ok(inquiries);
//    }

}
