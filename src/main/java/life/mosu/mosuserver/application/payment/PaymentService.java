package life.mosu.mosuserver.application.payment;

import static life.mosu.mosuserver.domain.discount.DiscountPolicy.FIXED_QUANTITY;

import java.util.List;
import life.mosu.mosuserver.domain.application.ApplicationJpaRepository;
import life.mosu.mosuserver.domain.discount.DiscountPolicy;
import life.mosu.mosuserver.domain.payment.PaymentJpaEntity;
import life.mosu.mosuserver.domain.payment.PaymentRepository;
import life.mosu.mosuserver.infra.payment.TossPaymentClient;
import life.mosu.mosuserver.infra.payment.dto.ConfirmTossPaymentResponse;
import life.mosu.mosuserver.presentation.payment.dto.CancelPaymentRequest;
import life.mosu.mosuserver.presentation.payment.dto.PaymentPrepareResponse;
import life.mosu.mosuserver.presentation.payment.dto.PaymentRequest;
import life.mosu.mosuserver.presentation.payment.dto.PreparePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * 영속화 처리 이미 들어올 때 할인 정책을 포함해야함
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ApplicationJpaRepository applicationJpaRepository;

    private final TossPaymentClient tossPayment;
    private final OrderIdGenerator orderIdGenerator;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher publisher;

    public PaymentPrepareResponse prepare(PreparePaymentRequest request) {
        // TODO: 인원 수 체크
        /**
         * 인원 수 redis에 동기화 -> 인원수가 넘어가면, application 까지 rollback
         */
        String uuid = orderIdGenerator.generate();
        int applicationCount = request.getSize();
        int totalAmount = DiscountPolicy.calculate(FIXED_QUANTITY, applicationCount);

        return PaymentPrepareResponse.of(uuid, totalAmount);
    }

    @Transactional
    @Retryable(retryFor = {HttpStatusCodeException.class})
    public void confirm(PaymentRequest request) {
        String orderId = request.orderId();
        List<Long> applicationIds = request.applicationIds();
        Integer amount = request.amount();
        try {
            checkApplicationsExist(request.applicationIds());
            verifyAmount(request.applicantSize(), request.amount());
            checkDuplicatePayment(orderId);
            ConfirmTossPaymentResponse response = confirmPaymentWithToss(request);
            List<PaymentJpaEntity> paymentEntities = mapToPaymentEntities(request, response);
            verifyPaymentSuccess(paymentEntities);
            savePayments(paymentEntities);
            publisher.publishEvent(PaymentEvent.ofSuccess(applicationIds, orderId, amount));
        } catch (Exception ex) {
            log.error("error : {}", ex.getMessage());
            publisher.publishEvent(PaymentEvent.ofFailed(applicationIds, orderId, amount));
            throw ex;
        }
    }

    @Recover
    public void recoverConfirm() {

    }

    @Transactional
    public void cancel(String paymentId, CancelPaymentRequest request) {
        //환불이 가능한가?
        tossPayment.cancelPayment(paymentId, request);
        // 환불 정책
        // 영속화 해지할 필요 X
        // 영속화 된 거에서 환불 상태로 변경
    }


    private void checkApplicationsExist(List<Long> applicationIds) {
        boolean existsAll = applicationJpaRepository.existsAllByIds(applicationIds,
                applicationIds.size());
        if (!existsAll) {
            log.warn("Application IDs not found: {}", applicationIds);
            throw new RuntimeException("존재하지 않는 신청입니다.");
        }
    }

    private void verifyAmount(int applicationCount, int requestedAmount) {
        int expectedAmount = DiscountPolicy.calculate(FIXED_QUANTITY, applicationCount);
        if (requestedAmount != expectedAmount) {
            log.warn("Payment amount mismatch: requested={}, expected={}", requestedAmount,
                    expectedAmount);
            throw new RuntimeException("결제 금액이 올바르지 않습니다.");
        }
    }

    private void checkDuplicatePayment(String orderId) {
        if (paymentRepository.existsByOrderId(orderId)) {
            log.warn("Duplicate payment orderId: {}", orderId);
            throw new RuntimeException("이미 존재하는 결제 건 입니다.");
        }
    }

    private ConfirmTossPaymentResponse confirmPaymentWithToss(PaymentRequest request) {
        try {
            ConfirmTossPaymentResponse response = tossPayment.confirmPayment(request.toPayload());

            log.info("Toss payment confirmed successfully: orderId={}", request.orderId());
            return response;
//            return new ConfirmTossPaymentResponse(
//                    "tviva20250702231345mODA4",     // paymentKey
//                    "IDMAoki7azYp8SzQ06LMt12323235", // orderId
//                    "DONE",                          // status
//                    "2025-07-02T23:14:33+09:00",    // approvedAt
//                    1_000,                           // totalAmount
//                    1_000,                           // balanceAmount
//                    1_000,                           // suppliedAmount
//                    1_000,                           // vat
//                    1_000,                           // taxFreeAmount
//                    "간편결제"                            // method
//            );
        } catch (Exception ex) {
            log.error("Toss payment confirmation failed for orderId={}", request.orderId(), ex);
            throw ex;  // @Retryable 에 의해 재시도 됨
        }
    }

    private List<PaymentJpaEntity> mapToPaymentEntities(PaymentRequest request,
            ConfirmTossPaymentResponse response) {
        return request.applicationIds().stream()
                .map(response::toEntity)
                .toList();
    }

    private void verifyPaymentSuccess(List<PaymentJpaEntity> paymentEntities) {
        List<Long> failedIds = paymentEntities.stream()
                .filter(p -> !p.getPaymentStatus().isPaySuccess())
                .map(PaymentJpaEntity::getId)
                .toList();
        if (!failedIds.isEmpty()) {
            log.error("Payment failed for application IDs: {}", failedIds);
            throw new RuntimeException("결제가 실패한 신청서가 있습니다: " + failedIds);
        }
    }

    private void savePayments(List<PaymentJpaEntity> paymentEntities) {
        try {
            paymentRepository.saveAll(paymentEntities);
            log.info("Payment records saved for {} payments", paymentEntities.size());
        } catch (Exception ex) {
            log.error("Failed to save payment records", ex);
            throw ex;  // 트랜잭션 롤백 유도
        }
    }
}
