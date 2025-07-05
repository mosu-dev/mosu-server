package life.mosu.mosuserver.presentation.admin;

import jakarta.validation.Valid;
import life.mosu.mosuserver.application.admin.AdminService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.admin.dto.StudentFilter;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponseWrapper<Page<StudentListResponse>>> getStudents(
            @Valid @ModelAttribute StudentFilter filter,
            Pageable pageable
    ) {
        Page<StudentListResponse> result = adminService.getStudents(filter, pageable);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "학생 목록 조회 성공", result));
    }
}
