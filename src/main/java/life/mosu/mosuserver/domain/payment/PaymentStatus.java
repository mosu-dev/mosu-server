package life.mosu.mosuserver.domain.payment;

public enum PaymentStatus {
    PENDING,           // 결제 대기
    COMPLETED,         // 결제 완료
    FAILED,            // 결제 실패
    CANCELLED,         // 결제 취소
    REFUND_REQUESTED,  // 환불 요청
    REFUND_APPROVED,   // 환불 승인
    REFUND_COMPLETED   // 환불 완료
}