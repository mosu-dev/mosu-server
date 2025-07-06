package life.mosu.mosuserver.application.payment;

import life.mosu.mosuserver.domain.payment.PaymentStatus;

public record PaymentEvent(String applicationId, String orderId, PaymentStatus status) {

    public static PaymentEvent ofSuccess(String applicationId, String orderId) {
        return new PaymentEvent(applicationId, orderId, PaymentStatus.DONE);
    }

    public static PaymentEvent ofCancelled(String applicationId, String orderId) {
        return new PaymentEvent(applicationId, orderId, PaymentStatus.CANCELLED_DONE);
    }

    public static PaymentEvent ofFailed(String applicationId, String orderId) {
        return new PaymentEvent(applicationId, orderId, PaymentStatus.ABORTED);
    }

    @Override
    public String toString() {
        return "PaymentEvent{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                '}';
    }
}
