package life.mosu.mosuserver.presentation.payment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PreparePaymentRequest(
        @NotEmpty(message = "결제 항목은 비어 있을 수 없습니다.")
        List<@Valid Item> items
) {

    public record Item(
            @NotNull(message = "applicationId는 필수입니다.")
            Long applicationId,

            @NotNull(message = "name은 필수입니다.")
            String name
    ) {

    }
}
