package life.mosu.mosuserver.presentation.profile;

import jakarta.validation.Valid;
import life.mosu.mosuserver.application.profile.RecommenderService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.profile.dto.RecommenderRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommender")
public class RecommenderController implements RecommenderControllerDocs {

    private final RecommenderService recommenderService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Void>> regist(
            @RequestParam Long userId,
            @Valid @RequestBody RecommenderRegistrationRequest request) {
        recommenderService.registerRecommender(userId, request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.CREATED, "추천인 등록 성공"));
    }

    @Override
    @GetMapping("/verify")
    public ResponseEntity<ApiResponseWrapper<Boolean>> verify(
            @RequestParam Long userId) {
        Boolean isRegistered = recommenderService.verifyRecommender(userId);

        if (isRegistered) {
            return ResponseEntity.ok(
                    ApiResponseWrapper.success(HttpStatus.OK, "추천인 등록이 불가능합니다.", isRegistered));
        }
        return ResponseEntity.ok(
                ApiResponseWrapper.success(HttpStatus.OK, "추천인 등록이 가능합니다.", isRegistered));
    }

}
