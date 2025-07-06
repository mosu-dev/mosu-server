package life.mosu.mosuserver.domain.payment;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum PaymentMethod {
    EASY_PAY("간편결제"),
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌");

    private final String name;

    public static PaymentMethod from(String raw) {
        return Arrays.stream(values())
                .filter(v -> v.name.equalsIgnoreCase(raw))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown payment method: " + raw));
    }
}
