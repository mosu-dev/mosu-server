package life.mosu.mosuserver.presentation.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import life.mosu.mosuserver.global.annotation.PhoneNumberPattern;
import life.mosu.mosuserver.global.util.FileRequest;

public record ApplicationRequest(
        FileRequest admissionTicket,
        @PhoneNumberPattern String guardianPhoneNumber,
        @PhoneNumberPattern String recommenderPhoneNumber,
        @NotNull Set<ApplicationSchoolRequest> schools,
        @NotNull AgreementRequest agreementRequest
) {

    public ApplicationJpaEntity toEntity(Long userId) {
        return ApplicationJpaEntity.builder()
                .userId(userId)
                .guardianPhoneNumber(guardianPhoneNumber)
                .recommenderPhoneNumber(recommenderPhoneNumber)
                .agreedToNotices(agreementRequest().agreedToNotices())
                .agreedToRefundPolicy(agreementRequest().agreedToRefundPolicy())
                .build();
    }
}
