package life.mosu.mosuserver.infra.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentErrorResponse {
    private String code;
    private String message;
}