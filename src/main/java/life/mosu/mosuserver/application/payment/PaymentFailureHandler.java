package life.mosu.mosuserver.application.payment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import life.mosu.mosuserver.domain.payment.PaymentJpaEntity;
import life.mosu.mosuserver.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentFailureHandler {

    private final PaymentRepository paymentRepository;

    public void handlePaymentFailure(PaymentEvent event) {
        List<PaymentJpaEntity> existingPayments = paymentRepository.findByOrderId(event.orderId());
        Set<Long> existingAppIds = existingPayments.stream()
                .map(PaymentJpaEntity::getApplicationId)
                .collect(Collectors.toSet());

        List<Long> missingAppIds = event.applicationIds().stream()
                .filter(appId -> !existingAppIds.contains(appId))
                .toList();

        // 상태 변경
        existingPayments.forEach(payment -> payment.changeStatus(event.status()));

        // 실패 신규 엔티티 생성
        List<PaymentJpaEntity> newPayments = missingAppIds.stream()
                .map(appId -> PaymentJpaEntity.ofFailure(
                        appId,
                        event.orderId(),
                        event.status(),
                        event.totalAmount()))
                .toList();

        paymentRepository.saveAll(existingPayments);
        paymentRepository.saveAll(newPayments);
    }
}
