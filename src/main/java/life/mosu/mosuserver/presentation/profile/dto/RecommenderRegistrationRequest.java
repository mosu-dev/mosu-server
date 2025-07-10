package life.mosu.mosuserver.presentation.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;

public record RecommenderRegistrationRequest(
        @Schema(description = "추천인 전화번호 (전화번호 형식은 010-XXXX-XXXX 이어야 합니다.)", example = "010-8765-4322")
        @PhoneNumberPattern
        String phoneNumber
) {

}