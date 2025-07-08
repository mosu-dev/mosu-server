package life.mosu.mosuserver.presentation.application;

import jakarta.validation.Valid;
import java.util.List;
import life.mosu.mosuserver.application.application.ApplicationService;
import life.mosu.mosuserver.global.annotation.UserId;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.application.dto.ApplicationRequest;
import life.mosu.mosuserver.presentation.application.dto.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    //신청
    @PostMapping
    @PreAuthorize("isAuthenticated() and hasRole('USER')")
    public ApiResponseWrapper<ApplicationResponse> apply(
            @UserId Long userId,
            @Valid @RequestBody ApplicationRequest request
    ) {
        ApplicationResponse response = applicationService.apply(userId, request);
        return ApiResponseWrapper.success(HttpStatus.OK, "신청 성공", response);
    }

    //전체 신청 내역 조회
    @GetMapping
    public ApiResponseWrapper<List<ApplicationResponse>> getApplications(
            @UserId Long userId
    ) {
        List<ApplicationResponse> responses = applicationService.getApplications(userId);
        return ApiResponseWrapper.success(HttpStatus.OK, "신청 내역 조회 성공", responses);
    }

}
