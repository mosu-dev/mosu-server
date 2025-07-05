package life.mosu.mosuserver.presentation.admin;

import org.springframework.data.domain.Pageable;
import life.mosu.mosuserver.application.admin.AdminService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/students")
    public ApiResponseWrapper<Page<StudentListResponse>> getStudents(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String phone,
        @RequestParam(defaultValue = "desc") String order,
        Pageable pageable
    ) {
        Page<StudentListResponse> result = adminService.getStudents(name, phone, order, pageable);
        return ApiResponseWrapper.success(HttpStatus.OK, "학생 목록 조회 성공", result);
    }
}
