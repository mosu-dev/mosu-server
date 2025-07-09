package life.mosu.mosuserver.presentation.faq;

import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.application.faq.FaqService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;
import life.mosu.mosuserver.presentation.faq.dto.FaqResponse;
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
public class FaqController {

    private final FaqService faqService;

    //TODO: 관리자 권한 체크 추가
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Void>> create(
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

    @PutMapping("/{faqId}")
    public ResponseEntity<ApiResponseWrapper<Void>> update(
            @PathVariable Long faqId,
            @Valid @RequestBody FaqCreateRequest request
    ) {
        faqService.update(request, faqId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 수정 성공"));
    }

    //TODO: 관리자 권한 체크 추가
    @DeleteMapping("/{faqId}")
    public ResponseEntity<ApiResponseWrapper<Void>> delete(@PathVariable Long faqId) {
        faqService.deleteFaq(faqId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 삭제 성공"));
    }
}
