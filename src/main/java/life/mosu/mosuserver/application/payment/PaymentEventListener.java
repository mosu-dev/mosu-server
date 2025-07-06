package life.mosu.mosuserver.application.payment;

import life.mosu.mosuserver.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentRepository paymentRepository;
    private final PaymentFailureHandler paymentFailureHandler;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommitHandler(PaymentEvent event) {
        log.debug("[BEFORE_COMMIT] 커밋 직전 처리: orderId={}", event.orderId());
        // 예: 캐시 업데이트, 커밋 직전 검증
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommitHandler(PaymentEvent event) {
        log.info("[AFTER_COMMIT] 커밋 성공 후 처리: orderId={}", event.orderId());
        // 예: 외부 API 호출, 메시지 큐 발행, 알림 전송
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void afterRollbackHandler(PaymentEvent event) {
        log.warn("[AFTER_ROLLBACK] 롤백 후 처리 시작: orderId={}", event.orderId());
        paymentFailureHandler.handlePaymentFailure(event);
        log.info("[AFTER_ROLLBACK] 롤백 후 처리 완료: orderId={}", event.orderId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void afterCompletionHandler(PaymentEvent event) {
        log.debug("[AFTER_COMPLETION] 커밋/롤백 후 무조건 처리: orderId={}", event.orderId());
        // 리소스 정리, 상태 초기화 등
    }

    @Recover
    public void recoverAfterRollbackHandler(Exception ex, PaymentEvent event) {
        log.error("[RECOVER] 롤백 후 처리 재시도 실패: orderId={}, error={}", event.orderId(),
                ex.getMessage(), ex);
    }

}

