package life.mosu.mosuserver.presentation.inquiry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import life.mosu.mosuserver.domain.inquiry.InquiryStatus;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryAnswerRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryAnswerUpdateRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryCreateRequest;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryDetailResponse;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Inquiry API", description = "1:1 문의 관련 API 명세")
public interface InquiryControllerDocs {

    @Operation(summary = "1:1 문의 등록", description = "사용자가 새로운 1:1 문의를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "질문 등록 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> create(
            @Parameter(description = "문의 생성에 필요한 정보") @RequestBody @Valid InquiryCreateRequest request
    );

    @Operation(summary = "1:1 문의 목록 조회", description = "조건에 맞는 1:1 문의 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    ResponseEntity<ApiResponseWrapper<Page<InquiryResponse>>> getInquiryList(
            @Parameter(name = "status", description = "문의 상태 (PENDING, COMPLETED)", in = ParameterIn.QUERY)
            @RequestParam(required = false) InquiryStatus status,
            @Parameter(name = "sort", description = "정렬 기준 필드", in = ParameterIn.QUERY)
            @RequestParam(required = false, defaultValue = "id") String sort,
            @Parameter(name = "asc", description = "오름차순 정렬 여부", in = ParameterIn.QUERY)
            @RequestParam(required = false, defaultValue = "true") boolean asc,
            @Parameter(hidden = true) Pageable pageable
    );

    @Operation(summary = "1:1 문의 상세 조회", description = "특정 1:1 문의의 상세 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 상세 조회 성공")
    })
    ResponseEntity<ApiResponseWrapper<InquiryDetailResponse>> getInquiryDetail(
            @Parameter(name = "postId", description = "조회할 문의의 ID", in = ParameterIn.PATH)
            @PathVariable Long postId
    );

    @Operation(summary = "1:1 문의 삭제", description = "특정 1:1 문의를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 삭제 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> deleteInquiry(
            @Parameter(name = "postId", description = "삭제할 문의의 ID", in = ParameterIn.PATH)
            @PathVariable Long postId
    );

    @Operation(summary = "문의 답변 등록 (관리자용)", description = "특정 문의에 대한 답변을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "답변 등록 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> inquiryAnswer(
            @Parameter(name = "postId", description = "답변을 등록할 문의의 ID", in = ParameterIn.PATH)
            @PathVariable Long postId,
            @Parameter(description = "답변 내용")
            @RequestBody InquiryAnswerRequest request
    );

    @Operation(summary = "문의 답변 수정 (관리자용)", description = "특정 문의에 대한 답변을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "답변 수정 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> updateInquiryAnswer(
            @Parameter(name = "postId", description = "답변을 수정할 문의의 ID", in = ParameterIn.PATH)
            @PathVariable Long postId,
            @Parameter(description = "수정할 답변 내용")
            @RequestBody InquiryAnswerUpdateRequest request
    );

    @Operation(summary = "문의 답변 삭제 (관리자용)", description = "특정 문의에 대한 답변을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "답변 삭제 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> deleteInquiryAnswer(
            @Parameter(name = "postId", description = "답변을 삭제할 문의의 ID", in = ParameterIn.PATH)
            @PathVariable Long postId
    );
}
