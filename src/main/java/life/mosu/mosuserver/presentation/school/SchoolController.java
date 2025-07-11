package life.mosu.mosuserver.presentation.school;

import java.util.List;
import life.mosu.mosuserver.application.school.SchoolService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.school.dto.SchoolRegistrationRequest;
import life.mosu.mosuserver.presentation.school.dto.SchoolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Void>> create(
            @RequestBody SchoolRegistrationRequest request
    ) {
        schoolService.registerSchool(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.success(HttpStatus.CREATED, "학교 등록 성공"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<SchoolResponse>>> getSchools() {

        List<SchoolResponse> schools = schoolService.getSchools();

        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "학교 조회 성공", schools));
    }
}
