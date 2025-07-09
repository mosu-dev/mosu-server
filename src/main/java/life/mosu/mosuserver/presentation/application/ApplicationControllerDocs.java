package life.mosu.mosuserver.presentation.application;

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
import life.mosu.mosuserver.presentation.application.dto.ApplicationRequest;
import life.mosu.mosuserver.presentation.application.dto.ApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Application API", description = "신청 관련 API 명세")
public interface ApplicationControllerDocs {

    @Operation(summary = "신청 등록", description = "사용자가 신청을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "신청 성공")
    })
    ResponseEntity<ApiResponseWrapper<ApplicationResponse>> apply(
            @Parameter(name = "userId", description = "사용자 ID", in = ParameterIn.QUERY)
            @RequestParam Long userId,

            @Parameter(description = "신청 요청 정보", required = true)
            @Valid @RequestBody ApplicationRequest request
    );

    @Operation(summary = "전체 신청 내역 조회", description = "사용자의 전체 신청 내역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "신청 내역 조회 성공",
                    content = @Content(schema = @Schema(implementation = ApplicationResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<List<ApplicationResponse>>> getApplications(
            @Parameter(name = "userId", description = "사용자 ID", in = ParameterIn.QUERY)
            @RequestParam Long userId
    );
}