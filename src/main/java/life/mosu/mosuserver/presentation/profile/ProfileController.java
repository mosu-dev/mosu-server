package life.mosu.mosuserver.presentation.profile;

import jakarta.validation.Valid;
import life.mosu.mosuserver.application.profile.ProfileService;
import life.mosu.mosuserver.global.annotation.UserId;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.profile.dto.EditProfileRequest;
import life.mosu.mosuserver.presentation.profile.dto.ProfileDetailResponse;
import life.mosu.mosuserver.presentation.profile.dto.ProfileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ApiResponseWrapper<Void> create(
            @UserId Long userId,
            @Valid @RequestBody ProfileRequest request
    ) {
        profileService.registerProfile(userId, request);
        return ApiResponseWrapper.success(HttpStatus.CREATED, "프로필 등록 성공");
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponseWrapper<Void> update(
            @UserId Long userId,
            @Valid @RequestBody EditProfileRequest request
    ) {
        profileService.editProfile(userId, request);
        return ApiResponseWrapper.success(HttpStatus.OK, "프로필 수정 성공");
    }

    @GetMapping
    public ApiResponseWrapper<ProfileDetailResponse> getProfile(
            @UserId Long userId) {
        ProfileDetailResponse response = profileService.getProfile(userId);
        return ApiResponseWrapper.success(HttpStatus.OK, "프로필 조회 성공", response);
    }

}
