package life.mosu.mosuserver.application.payment;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PaymentEventListener {

    // 1. 트랜잭션 커밋 직전에 호출됨 (롤백 가능 상태)
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommitHandler(PaymentEvent event) {
        System.out.println("[BEFORE_COMMIT] 커밋 직전 처리: " + event.orderId());
        // 예) 캐시 업데이트, 커밋 직전 검증 등
    }

    // 2. 트랜잭션 성공적으로 커밋된 후 호출 (기본값)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommitHandler(PaymentEvent event) {
        System.out.println("[AFTER_COMMIT] 커밋 성공 후 처리: " + event.orderId());
        // 예) 외부 API 호출, 메시지 큐 발행, 알림 전송
    }

    // 3. 트랜잭션이 롤백된 후 호출됨
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void afterRollbackHandler(PaymentEvent event) {
        System.out.println("[AFTER_ROLLBACK] 롤백 후 처리: " + event.orderId());
        // 예) 보상 트랜잭션, 로그 기록, 장애 알림
    }

    // 4. 트랜잭션 커밋 성공 또는 롤백 후 무조건 호출됨
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void afterCompletionHandler(PaymentEvent event) {
        System.out.println("[AFTER_COMPLETION] 커밋/롤백 후 무조건 처리: " + event.orderId());
        // 예) 리소스 정리, 상태 초기화 등
    }
}
