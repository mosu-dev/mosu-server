package life.mosu.mosuserver.presentation.notice;

import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.application.notice.NoticeService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.notice.dto.NoticeCreateRequest;
import life.mosu.mosuserver.presentation.notice.dto.NoticeResponse;
import life.mosu.mosuserver.presentation.notice.dto.NoticeUpdateRequest;
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
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // TODO: 관리자 권한 체크 추가
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Void>> createNotice(
            @Valid @RequestBody NoticeCreateRequest request) {
        noticeService.createNotice(request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.CREATED, "게시글 등록 성공"));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponseWrapper<List<NoticeResponse>>> getNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<NoticeResponse> notices = noticeService.getNoticeWithAttachments(page, size);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 조회 성공", notices));
    }

    // TODO: 관리자 권한 체크 추가
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 삭제 성공"));
    }

    // TODO: 관리자 권한 체크 추가
    @PutMapping("/{noticeId}")
    public ResponseEntity<ApiResponseWrapper<Void>> updateNotice(
            @PathVariable Long noticeId,
            @Valid @RequestBody NoticeUpdateRequest request
    ) {
        noticeService.updateNotice(noticeId, request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 수정 성공"));
    }
}