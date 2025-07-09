package life.mosu.mosuserver.presentation.admin;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import life.mosu.mosuserver.application.admin.AdminService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.global.util.excel.SimpleExcelFile;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationExcelDto;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationFilter;
import life.mosu.mosuserver.presentation.admin.dto.ApplicationListResponse;
import life.mosu.mosuserver.presentation.admin.dto.SchoolLunchResponse;
import life.mosu.mosuserver.presentation.admin.dto.StudentExcelDto;
import life.mosu.mosuserver.presentation.admin.dto.StudentFilter;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/students")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<Page<StudentListResponse>>> getStudents(
            @Valid @ModelAttribute StudentFilter filter,
            Pageable pageable
    ) {
        Page<StudentListResponse> result = adminService.getStudents(filter, pageable);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "학생 목록 조회 성공", result));
    }

    @GetMapping("/excel/students")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public void downloadStudentInfo(
            HttpServletResponse response) throws IOException {
        String fileName = URLEncoder.encode("학생정보목록.xlsx", StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        List<StudentExcelDto> data = adminService.getStudentExcelData();
        SimpleExcelFile<StudentExcelDto> excelFile = new SimpleExcelFile<>(data,
                StudentExcelDto.class);

        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/lunches")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<List<SchoolLunchResponse>>> getLunchCounts() {
        List<SchoolLunchResponse> result = adminService.getLunchCounts();
        return ResponseEntity.ok(
                ApiResponseWrapper.success(HttpStatus.OK, "학교별 도시락 신청 수 조회 성공", result));
    }

    @GetMapping("/applications")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponseWrapper<Page<ApplicationListResponse>>> getApplications(
            @Valid @ModelAttribute ApplicationFilter filter,
            Pageable pageable
    ) {
        Page<ApplicationListResponse> result = adminService.getApplications(filter, pageable);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "신청 목록 조회 성공", result));
    }

    @GetMapping("/excel/applications")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public void downloadApplicationInfo(HttpServletResponse response) throws IOException {
        String fileName = URLEncoder.encode("신청 목록.xlsx", StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        List<ApplicationExcelDto> data = adminService.getApplicationExcelData();
        SimpleExcelFile<ApplicationExcelDto> excelFile = new SimpleExcelFile<>(data,
                ApplicationExcelDto.class);

        excelFile.write(response.getOutputStream());
    }

}
