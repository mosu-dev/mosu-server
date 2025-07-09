package life.mosu.mosuserver.presentation.auth;

import jakarta.validation.Valid;
import life.mosu.mosuserver.application.auth.SignUpService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.auth.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/signup")
@RequiredArgsConstructor
public class SignUpController implements SignUpControllerDocs {

    private final SignUpService signUpService;

    /**
     * 회원 가입
     *
     * @param request 회원 가입 요청
     */
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Void>> signUp(
            @RequestBody @Valid final SignUpRequest request
    ) {
        signUpService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.success(HttpStatus.CREATED, "회원가입 성공"));
    }
}