package life.mosu.mosuserver.presentation.faq;

import life.mosu.mosuserver.applicaiton.faq.FaqService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {
    private final FaqService faqService;

    @PostMapping("/create")
    public ApiResponseWrapper<Void> create(@ModelAttribute FaqCreateRequest request) {
        faqService.createFaq(request);
        return ApiResponseWrapper.success(HttpStatus.CREATED, "게시글 등록 성공");
    }

    @DeleteMapping("/delete/{faqId}")
    public ApiResponseWrapper<Void> delete(@PathVariable Long faqId) {
        faqService.deleteFaq(faqId);
        return ApiResponseWrapper.success(HttpStatus.OK, "게시글 삭제 성공");
    }
}
