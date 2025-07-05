package life.mosu.mosuserver.presentation.application.dto;

public record AgreementRequest(
    boolean agreedToNotices,
    boolean agreedToRefundPolicy
) {
}
