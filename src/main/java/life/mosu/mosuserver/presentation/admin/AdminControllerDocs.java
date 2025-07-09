package life.mosu.mosuserver.presentation.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationFilter;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationListResponse;
import life.mosu.mosuserver.presentation.admin.dto.SchoolLunchResponse;
import life.mosu.mosuserver.presentation.admin.dto.StudentFilter;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Admin API", description = "관리자용 데이터 조회 및 엑셀 다운로드 API 명세")
public interface AdminControllerDocs {

    @Operation(summary = "학생 목록 조회", description = "필터 조건에 따른 학생 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "학생 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = StudentListResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<Page<StudentListResponse>>> getStudents(
            @Parameter(description = "학생 목록 조회 필터")
            StudentFilter filter,

            @Parameter(hidden = true)
            Pageable pageable
    );

    @Operation(summary = "학생 정보 엑셀 다운로드", description = "전체 학생 정보를 엑셀 파일로 다운로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "엑셀 다운로드 성공 (응답은 바이너리)")
    })
    void downloadStudentInfo(
            @Parameter(hidden = true) HttpServletResponse response
    ) throws IOException;

    @Operation(summary = "학교별 도시락 신청 수 조회", description = "학교별 도시락 신청 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도시락 신청 수 조회 성공",
                    content = @Content(schema = @Schema(implementation = SchoolLunchResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<List<SchoolLunchResponse>>> getLunchCounts();

    @Operation(summary = "신청 목록 조회", description = "필터 조건에 따른 신청 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "신청 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ApplicationListResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<Page<ApplicationListResponse>>> getApplications(
            @Parameter(description = "신청 목록 조회 필터")
            ApplicationFilter filter,

            @Parameter(hidden = true)
            Pageable pageable
    );

    @Operation(summary = "신청 목록 엑셀 다운로드", description = "전체 신청 목록을 엑셀 파일로 다운로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "엑셀 다운로드 성공")
    })
    void downloadApplicationInfo(
            @Parameter(hidden = true) HttpServletResponse response
    ) throws IOException;

}