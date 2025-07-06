package life.mosu.mosuserver.presentation.payment.dto;

import jakarta.validation.constraints.NotNull;

public record CancelPaymentRequest(
       @NotNull String cancelReason
) {}