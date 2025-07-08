package life.mosu.mosuserver.presentation.notice;

import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.application.notice.NoticeService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.notice.dto.NoticeCreateRequest;
import life.mosu.mosuserver.presentation.notice.dto.NoticeDetailResponse;
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

    /**
     * Handles HTTP POST requests to create a new notice.
     *
     * Accepts a validated notice creation request and delegates the creation to the service layer.
     * Returns a standardized API response indicating successful creation with HTTP status 201 (Created).
     *
     * @param request the validated notice creation request payload
     * @return a response entity containing the API response wrapper with a success message
     */
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Void>> createNotice(
            @Valid @RequestBody NoticeCreateRequest request) {
        noticeService.createNotice(request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.CREATED, "게시글 등록 성공"));
    }

    /**
     * Retrieves a paginated list of notices with their attachments.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of notices per page (default is 10)
     * @return a response entity containing a standardized API response with the list of notices
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponseWrapper<List<NoticeResponse>>> getNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<NoticeResponse> notices = noticeService.getNoticeWithAttachments(page, size);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 조회 성공", notices));
    }

    /**
     * Retrieves detailed information for a specific notice by its ID.
     *
     * @param noticeId the unique identifier of the notice to retrieve
     * @return a response entity containing the detailed notice information wrapped in an API response
     */
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponseWrapper<NoticeDetailResponse>> getNoticeDetail(
            @PathVariable Long noticeId) {
        NoticeDetailResponse notice = noticeService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 상세 조회 성공", notice));
    }

    /**
     * Deletes a specific notice identified by its ID.
     *
     * @param noticeId the unique identifier of the notice to delete
     * @return a success response indicating the notice was deleted
     */
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 삭제 성공"));
    }

    /**
     * Updates an existing notice with the provided information.
     *
     * @param noticeId the ID of the notice to update
     * @param request the updated notice data
     * @return a success response indicating the notice was updated
     */
    @PutMapping("/{noticeId}")
    public ResponseEntity<ApiResponseWrapper<Void>> updateNotice(
            @PathVariable Long noticeId,
            @Valid @RequestBody NoticeUpdateRequest request
    ) {
        noticeService.updateNotice(noticeId, request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "게시글 수정 성공"));
    }
}