package life.mosu.mosuserver.presentation.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.auth.dto.LoginRequest;
import life.mosu.mosuserver.presentation.auth.dto.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(description = "인증 API", name = "Auth API")
public interface AuthControllerDocs {

    @Operation(description = "로그인 API 지금은 쿠키와 response 둘다 반환하는데 곧 쿠키로만 작동하게 할 것 입니다.", summary = "사용자가 로그인합니다.")
    public ResponseEntity<ApiResponseWrapper<Token>> login(
            @RequestBody @Valid final LoginRequest request);

    @Operation(description = "수정될 예정 입니다.")
    public ResponseEntity<ApiResponseWrapper<Token>> reissueAccessToken(
            final HttpServletRequest servletRequest);
}
