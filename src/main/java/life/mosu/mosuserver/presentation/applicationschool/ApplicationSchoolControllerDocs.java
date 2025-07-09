package life.mosu.mosuserver.presentation.applicationschool;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.application.dto.ApplicationSchoolResponse;
import life.mosu.mosuserver.presentation.applicationschool.dto.AdmissionTicketResponse;
import life.mosu.mosuserver.presentation.applicationschool.dto.RefundRequest;
import life.mosu.mosuserver.presentation.applicationschool.dto.SubjectUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Application School API", description = "개별 신청 학교 관련 API 명세")
public interface ApplicationSchoolControllerDocs {

    @Operation(summary = "신청 학교 취소", description = "사용자가 신청한 학교를 취소 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "취소 및 환불 처리 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> cancelApplicationSchool(
            @Parameter(name = "applicationSchoolId", description = "신청 학교 ID", in = ParameterIn.PATH)
            @PathVariable Long applicationSchoolId,

            @Parameter(name = "userId", description = "사용자 ID", in = ParameterIn.QUERY)
            @PathVariable Long userId,

            @Parameter(description = "환불 요청 정보", required = true)
            @RequestBody @Valid RefundRequest request
    );

    @Operation(summary = "신청 과목 수정", description = "신청한 학교에 대해 과목 목록을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "과목 수정 성공",
                    content = @Content(schema = @Schema(implementation = ApplicationSchoolResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<ApplicationSchoolResponse>> updateSubjects(
            @Parameter(name = "applicationSchoolId", description = "신청 학교 ID", in = ParameterIn.PATH)
            @PathVariable Long applicationSchoolId,

            @Parameter(name = "userId", description = "사용자 ID", in = ParameterIn.QUERY)
            @PathVariable Long userId,

            @Parameter(description = "수정할 과목 목록", required = true)
            @RequestBody @Valid SubjectUpdateRequest request
    );

    @Operation(summary = "신청 상세 조회", description = "신청한 학교의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = ApplicationSchoolResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<ApplicationSchoolResponse>> getDetail(
            @Parameter(name = "userId", description = "사용자 ID", in = ParameterIn.QUERY)
            @PathVariable Long userId,

            @Parameter(name = "applicationSchoolId", description = "신청 학교 ID", in = ParameterIn.PATH)
            @PathVariable Long applicationSchoolId
    );

    @Operation(summary = "수험표 조회", description = "신청한 학교에 대한 수험표를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수험표 조회 성공",
                    content = @Content(schema = @Schema(implementation = AdmissionTicketResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<AdmissionTicketResponse>> getAdmissionTicket(
            @Parameter(name = "userId", description = "사용자 ID", in = ParameterIn.QUERY)
            @PathVariable Long userId,

            @Parameter(name = "applicationSchoolId", description = "신청 학교 ID", in = ParameterIn.PATH)
            @PathVariable Long applicationSchoolId
    );
}