package life.mosu.mosuserver.presentation.application;

import jakarta.validation.Valid;
import life.mosu.mosuserver.application.application.ApplicationService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.application.dto.ApplicationRequest;
import life.mosu.mosuserver.presentation.application.dto.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * 신청
     *
     * @param userId
     * @param request
     * @return
     */
    @PostMapping
    public ApiResponseWrapper<ApplicationResponse> apply(
        @RequestParam Long userId,
        @Valid @RequestBody ApplicationRequest request
    ) {
        ApplicationResponse response = applicationService.apply(userId, request);
        return ApiResponseWrapper.success(HttpStatus.OK, "신청 성공", response);
    }

    /**
     * 전체 신청 내역 조회
     */
    @GetMapping()
    public ApiResponseWrapper<List<ApplicationResponse>> getAll(
    ) {
        List<ApplicationResponse> responses = applicationService.getApplications(2L);
        return ApiResponseWrapper.success(HttpStatus.OK, "신청 내역 조회 성공", responses);
    }
}
