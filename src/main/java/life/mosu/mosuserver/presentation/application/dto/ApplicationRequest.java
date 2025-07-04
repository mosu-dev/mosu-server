package life.mosu.mosuserver.presentation.application.dto;

import jakarta.validation.constraints.NotNull;
import life.mosu.mosuserver.presentation.faq.dto.FileRequest;

import java.util.Set;

public record ApplicationRequest(
    FileRequest admissionTicket,
    @NotNull String recommenderPhoneNumber,
    @NotNull Set<ApplicationSchoolRequest> schools,
    @NotNull AgreementRequest agreementRequest, //이 부분 어떻게 저장하면 좋을지
    @NotNull Integer amount
) {
}
