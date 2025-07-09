package life.mosu.mosuserver.presentation.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpServiceTermRequest(
        @Schema(
                description = "모수 이용약관 (필수) 동의 여부",
                example = "true"
        )
        boolean agreedToTermsOfService,
        @Schema(
                description = "개인정보 수집 및 이용 동의 (필수) 동의 여부",
                example = "true"
        )
        boolean agreedToPrivacyPolicy,
        @Schema(
                description = "마케팅 정보 수신 (선택) 동의 여부",
                example = "false"
        )
        boolean agreedToMarketing
) {

}