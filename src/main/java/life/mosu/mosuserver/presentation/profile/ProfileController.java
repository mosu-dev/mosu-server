package life.mosu.mosuserver.presentation.profile;

import jakarta.validation.Valid;
import life.mosu.mosuserver.application.profile.ProfileService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.profile.dto.EditProfileRequest;
import life.mosu.mosuserver.presentation.profile.dto.ProfileDetailResponse;
import life.mosu.mosuserver.presentation.profile.dto.ProfileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ApiResponseWrapper<Void> create(
        @RequestParam Long userId,
        @Valid @RequestBody ProfileRequest request
    ) {
        profileService.registerProfile(userId, request);
        return ApiResponseWrapper.success(HttpStatus.CREATED, "프로필 등록 성공");
    }

    @PutMapping
    public ApiResponseWrapper<Void> update(
        @RequestParam Long userId,
        @Valid @RequestBody EditProfileRequest request
    ) {
        profileService.editProfile(userId, request);
        return ApiResponseWrapper.success(HttpStatus.OK, "프로필 수정 성공");
    }

    @GetMapping
    public ApiResponseWrapper<ProfileDetailResponse> getProfile(
        @RequestParam Long userId) {
        ProfileDetailResponse response = profileService.getProfile(userId);
        return ApiResponseWrapper.success(HttpStatus.OK, "프로필 조회 성공", response);
    }

}
