package life.mosu.mosuserver.presentation.faq;

import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.application.faq.FaqService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;
import life.mosu.mosuserver.presentation.faq.dto.FaqResponse;
import life.mosu.mosuserver.presentation.faq.dto.FaqUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController implements FaqControllerDocs {

    private final FaqService faqService;

    //TODO: 관리자 권한 체크 추가
    @PostMapping
    //    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<Void>> createFaq(
            @Valid @RequestBody FaqCreateRequest request) {
        faqService.createFaq(request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.CREATED, "게시글 등록 성공"));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponseWrapper<List<FaqResponse>>> getFaqs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<FaqResponse> responses = faqService.getFaqWithAttachments(page, size);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 조회 성공", responses));
    }

    @GetMapping("/{faqId}")
    public ResponseEntity<ApiResponseWrapper<FaqResponse>> getFaqDetail(
            @PathVariable Long faqId) {
        FaqResponse faq = faqService.getFaqDetail(faqId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 상세 조회 성공", faq));
    }

    @PutMapping("/{faqId}")
    //    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<Void>> updateFaq(
            @PathVariable Long faqId,
            @Valid @RequestBody FaqUpdateRequest request
    ) {
        faqService.update(request, faqId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 수정 성공"));
    }

    //TODO: 관리자 권한 체크 추가
    @DeleteMapping("/{faqId}")
    //    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteFaq(@PathVariable Long faqId) {
        faqService.deleteFaq(faqId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 삭제 성공"));
    }
}
