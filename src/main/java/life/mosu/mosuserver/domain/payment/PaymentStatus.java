package life.mosu.mosuserver.domain.payment;

import java.util.Arrays;

public enum PaymentStatus {
    //결제
    PREPARE,
    DONE,
    EXPIRED,
    ABORTED,
    //환불
    CANCELLED_DONE,
    CANCELLED_ABORTED;

    public static PaymentStatus from(String raw) {
        return Arrays.stream(values())
                    .filter(v -> v.name().equalsIgnoreCase(raw))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + raw));
    }

    public boolean isPaySuccess() {
        return this == DONE;
    }
}