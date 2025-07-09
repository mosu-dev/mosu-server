package life.mosu.mosuserver.presentation.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.auth.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(description = "회원가입 API", name = "Sign Up API")
public interface SignUpControllerDocs {

    @Operation(summary = "회원 가입", description = "사용자가 새로운 계정을 생성합니다.")
    public ResponseEntity<ApiResponseWrapper<Void>> signUp(
            @RequestBody @Valid final SignUpRequest request
    );

}
