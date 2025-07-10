package life.mosu.mosuserver.application.payment;

import java.util.List;
import life.mosu.mosuserver.domain.payment.PaymentStatus;

public record PaymentEvent(
        List<Long> applicationSchoolIds,
        String orderId,
        PaymentStatus status,
        Integer totalAmount
) {

    public static PaymentEvent ofSuccess(List<Long> applicationIds, String orderId,
            Integer totalAmount) {
        return new PaymentEvent(applicationIds, orderId, PaymentStatus.DONE, totalAmount);
    }

    public static PaymentEvent ofCancelled(List<Long> applicationIds, String orderId,
            Integer totalAmount) {
        return new PaymentEvent(applicationIds, orderId, PaymentStatus.CANCELLED_DONE, totalAmount);
    }

    public static PaymentEvent ofFailed(List<Long> applicationIds, String orderId,
            Integer totalAmount) {
        return new PaymentEvent(applicationIds, orderId, PaymentStatus.ABORTED, totalAmount);
    }

    @Override
    public String toString() {
        return "PaymentEvent{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                '}';
    }
}
