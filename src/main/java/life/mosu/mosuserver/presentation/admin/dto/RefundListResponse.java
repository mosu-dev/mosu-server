package life.mosu.mosuserver.presentation.admin.dto;

public record RefundListResponse(
        Long refundId,
        String examNumber,
        String name,
        String phone,
        String requestedAt,
        String completedAt,
        String paymentMethod,
        String reason

) {

}
