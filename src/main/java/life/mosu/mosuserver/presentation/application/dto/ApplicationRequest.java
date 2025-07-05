package life.mosu.mosuserver.presentation.application.dto;

import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import life.mosu.mosuserver.presentation.faq.dto.FileRequest;

import java.util.Set;

public record ApplicationRequest(
    FileRequest admissionTicket,
    String recommenderPhoneNumber,
    @NotNull Set<ApplicationSchoolRequest> schools,
    @NotNull AgreementRequest agreementRequest
) {
    public ApplicationJpaEntity toEntity(Long userId) {
        return ApplicationJpaEntity.builder()
            .userId(userId)
            .recommenderPhoneNumber(recommenderPhoneNumber)
            .agreedToNotices(agreementRequest().agreedToNotices())
            .agreedToRefundPolicy(agreementRequest().agreedToRefundPolicy())
            .build();
    }
}
