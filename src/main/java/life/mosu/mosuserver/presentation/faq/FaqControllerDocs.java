package life.mosu.mosuserver.presentation.faq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.faq.dto.FaqCreateRequest;
import life.mosu.mosuserver.presentation.faq.dto.FaqResponse;
import life.mosu.mosuserver.presentation.faq.dto.FaqUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Faq API", description = "FAQ 관련 API 명세")
public interface FaqControllerDocs {

    @Operation(summary = "FAQ 등록", description = "관리자가 새로운 FAQ를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FAQ 등록 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> createFaq(
            @Parameter(description = "FAQ 등록 요청 데이터") @RequestBody @Valid FaqCreateRequest request
    );

    @Operation(summary = "FAQ 목록 조회", description = "전체 FAQ 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FaqResponse.class))
                    )
            )

    })
    ResponseEntity<ApiResponseWrapper<List<FaqResponse>>> getFaqs(
            @Parameter(name = "page", description = "페이지 번호", in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "0") int page,

            @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "FAQ 상세 조회", description = "FAQ ID를 기반으로 상세 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ 상세 조회 성공")
    })
    ResponseEntity<ApiResponseWrapper<FaqResponse>> getFaqDetail(
            @Parameter(name = "faqId", description = "FAQ ID", in = ParameterIn.PATH)
            @PathVariable Long faqId
    );

    @Operation(summary = "FAQ 삭제", description = "FAQ ID를 기반으로 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ 삭제 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> deleteFaq(
            @Parameter(name = "faqId", description = "삭제할 FAQ ID", in = ParameterIn.PATH)
            @PathVariable Long faqId
    );

    @Operation(summary = "FAQ 수정", description = "기존 FAQ 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FAQ 수정 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> updateFaq(
            @Parameter(name = "faqId", description = "수정할 FAQ ID", in = ParameterIn.PATH)
            @PathVariable Long faqId,

            @Parameter(description = "FAQ 수정 요청 데이터")
            @RequestBody @Valid FaqUpdateRequest request
    );
}