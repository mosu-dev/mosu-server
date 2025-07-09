package life.mosu.mosuserver.presentation.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.profile.dto.EditProfileRequest;
import life.mosu.mosuserver.presentation.profile.dto.ProfileDetailResponse;
import life.mosu.mosuserver.presentation.profile.dto.ProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Profile API - 본인 인증 관련 로직 추가될 예정입니다.", description = "사용자 프로필 관련 API 명세")
public interface ProfileControllerDocs {

    @Operation(summary = "프로필 등록", description = "회원가입 이후 처음으로 프로필을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "프로필 등록 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> create(
            @Parameter(name = "userId", description = "사용자 ID", in = ParameterIn.QUERY)
            @RequestParam Long userId,

            @Parameter(description = "프로필 등록 요청 정보", required = true)
            @Valid @RequestBody ProfileRequest request
    );

    @Operation(summary = "프로필 수정", description = "회원이 자신의 프로필을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 성공")
    })
    ResponseEntity<ApiResponseWrapper<Void>> update(
            @Parameter(name = "userId", description = "회원 ID", in = ParameterIn.QUERY)
            @RequestParam Long userId,

            @Parameter(description = "프로필 수정 요청 정보", required = true)
            @Valid @RequestBody EditProfileRequest request
    );

    @Operation(summary = "프로필 조회", description = "회원이 자신의 프로필 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 조회 성공",
                    content = @Content(schema = @Schema(implementation = ProfileDetailResponse.class)))
    })
    ResponseEntity<ApiResponseWrapper<ProfileDetailResponse>> getProfile(
            @Parameter(name = "userId", description = "회원 ID", in = ParameterIn.QUERY)
            @RequestParam Long userId
    );
}