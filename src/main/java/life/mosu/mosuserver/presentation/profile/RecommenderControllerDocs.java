package life.mosu.mosuserver.presentation.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.profile.dto.RecommenderRegistrationRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "Recommender API", description = "추천인 관련 API")
public interface RecommenderControllerDocs {

    @Operation(description = "추천인 등록 API", summary = "추천인 등록은 전화번호로 등록가능합니다. 신청을 진행할때 호출하시면 됩니다. ")
    public ResponseEntity<ApiResponseWrapper<Void>> register(
            @Schema(description = "API 연동을 위한 사용자 쿼리파라미터 userId 인증 인가 적용때 변경 예정", example = "1")
            Long userId,
            RecommenderRegistrationRequest request
    );

    @Operation(description = "추천인 등록한지 검증하는 API", summary = "추천인 등록은 딱 한번만 할 수 있어서  검증하는 API입니다.")
    public ResponseEntity<ApiResponseWrapper<Boolean>> verify(
            @Schema(description = "API 연동을 위한 사용자 쿼리파라미터 userId 인증 인가 적용때 변경 예정", example = "1")
            Long userId
    );
}
