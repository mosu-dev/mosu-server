package life.mosu.mosuserver.presentation.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;
import life.mosu.mosuserver.global.util.FileRequest;

@Schema(description = "시험 신청 요청 DTO")
public record ApplicationRequest(

        @Schema(description = "수험표 파일 정보", implementation = FileRequest.class)
        FileRequest admissionTicket,

        @Schema(description = "보호자 전화번호 (전화번호 형식은 010-XXXX-XXXX 이어야 합니다.)", example = "010-1234-5678")
        @PhoneNumberPattern
        String guardianPhoneNumber,

        @Schema(description = "신청 학교 목록", required = true)
        @NotNull
        Set<ApplicationSchoolRequest> schools,

        @Schema(description = "약관 동의 정보", required = true)
        @NotNull
        AgreementRequest agreementRequest

) {

    public ApplicationJpaEntity toEntity(Long userId) {
        return ApplicationJpaEntity.builder()
                .userId(userId)
                .guardianPhoneNumber(guardianPhoneNumber)
                .agreedToNotices(agreementRequest().agreedToNotices())
                .agreedToRefundPolicy(agreementRequest().agreedToRefundPolicy())
                .build();
    }
}
