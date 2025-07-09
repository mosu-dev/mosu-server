package life.mosu.mosuserver.presentation.school;

import java.util.List;
import life.mosu.mosuserver.application.school.SchoolService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.school.dto.SchoolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<SchoolResponse>>> getSchools() {

        List<SchoolResponse> schools = schoolService.getSchools();

        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "학교 조회 성공", schools));
    }
}
