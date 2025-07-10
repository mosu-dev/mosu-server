package life.mosu.mosuserver.presentation.notice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.notice.dto.NoticeCreateRequest;
import life.mosu.mosuserver.presentation.notice.dto.NoticeDetailResponse;
import life.mosu.mosuserver.presentation.notice.dto.NoticeResponse;
import life.mosu.mosuserver.presentation.notice.dto.NoticeUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Notice API", description = "공지사항 관련 API 명세")
public interface NoticeControllerDocs {

    @Operation(summary = "공지사항 등록", description = "관리자가 새로운 공지사항을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "공지사항 등록 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> createNotice(
            @Parameter(description = "공지사항 생성에 필요한 정보") @Valid @RequestBody NoticeCreateRequest request
    );

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 리스트를 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = NoticeResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<List<NoticeResponse>>> getNotices(
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "공지사항 상세 조회", description = "특정 공지사항의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = NoticeDetailResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<NoticeDetailResponse>> getNoticeDetail(
            @Parameter(name = "noticeId", description = "조회할 공지사항 ID", in = ParameterIn.PATH)
            @PathVariable Long noticeId
    );

    @Operation(summary = "공지사항 삭제", description = "특정 공지사항을 삭제합니다. (관리자 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 삭제 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> deleteNotice(
            @Parameter(name = "noticeId", description = "삭제할 공지사항 ID", in = ParameterIn.PATH)
            @PathVariable Long noticeId
    );

    @Operation(summary = "공지사항 수정", description = "특정 공지사항을 수정합니다. (관리자 권한)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> updateNotice(
            @Parameter(name = "noticeId", description = "수정할 공지사항 ID", in = ParameterIn.PATH)
            @PathVariable Long noticeId,
            @Parameter(description = "수정할 공지사항 정보")
            @Valid @RequestBody NoticeUpdateRequest request
    );
}