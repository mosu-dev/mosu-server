package life.mosu.mosuserver.presentation.inquiry;

import life.mosu.mosuserver.application.inquiry.InquiryService;
import life.mosu.mosuserver.domain.inquiry.InquiryStatus;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryCreateRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/list")
    public ResponseEntity<ApiResponseWrapper<Page<InquiryResponse>>> getInquiryList(
            @RequestParam(required = false) InquiryStatus status,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "true") boolean asc,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<InquiryResponse> inquiries = inquiryService.getInquiries(status, sort, asc,
                pageable);
        return ResponseEntity.ok(
                ApiResponseWrapper.success(HttpStatus.OK, "질문 목록 조회 성공", inquiries));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseWrapper<InquiryDetailResponse>> getInquiryDetail(
            @PathVariable Long postId) {

        InquiryDetailResponse inquiry = inquiryService.getInquiryDetail(postId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "질문 상세 조회 성공",
                inquiry));
    }


}
