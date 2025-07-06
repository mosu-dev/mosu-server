package life.mosu.mosuserver.application.payment;

import life.mosu.mosuserver.domain.discount.DiscountPolicy;
import life.mosu.mosuserver.domain.payment.PaymentJpaEntity;
import life.mosu.mosuserver.domain.payment.PaymentRepository;
import life.mosu.mosuserver.infra.payment.TossPaymentClient;
import life.mosu.mosuserver.infra.payment.dto.ConfirmTossPaymentResponse;
import life.mosu.mosuserver.persistence.application.ApplicationSchoolJpaRepository;
import life.mosu.mosuserver.presentation.payment.dto.CancelPaymentRequest;
import life.mosu.mosuserver.presentation.payment.dto.PaymentPrepareResponse;
import life.mosu.mosuserver.presentation.payment.dto.PaymentRequest;
import life.mosu.mosuserver.presentation.payment.dto.PreparePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final TossPaymentClient tossPayment;
    private final OrderIdGenerator orderIdGenerator;
    private final PaymentRepository paymentRepository;
    private final ApplicationSchoolJpaRepository applicationSchoolJpaRepository;

    public PaymentPrepareResponse prepare(PreparePaymentRequest request) {
        // 인원 수 체크
        /**
         * 인원 수 redis에 동기화 -> 인원수가 넘어가면, application 까지 rollback
         */
        String uuid = orderIdGenerator.generate();
        int applicationCount = request
                .items()
                .size();
        int totalAmount = DiscountPolicy
                .QUANTITY_PERCENTAGE
                .calculateDiscount(applicationCount);

        return PaymentPrepareResponse.of(uuid, totalAmount);
    }

    @Transactional
    @Retryable(retryFor = {HttpStatusCodeException.class})
    public void confirm(PaymentRequest request) {
        // 1. 신청 조회 (가정: applicationService 또는 repository 호출)
        int applicationCount = 3; // 나중에 실제 DB에서 조회
        // TODO: 신청 상태 검증 (결제 가능 상태인지)

        //2. 금액 재계산 및 비교
        int totalAmount = DiscountPolicy.QUANTITY_PERCENTAGE.calculateDiscount(applicationCount);
        if (request.amount() != totalAmount) {
            throw new IllegalArgumentException("결제 금액이 올바르지 않습니다.");
        }

        // 3. 중복 결제 여부 확인 (예: paymentRepository.existsByApplicationIdAndStatusPaid)
        // TODO: 중복 결제 로직 추가
        if (paymentRepository.existsByOrderId(request.orderId())) {
            throw new IllegalArgumentException("이미 존재하는 결제 건 입니다.");
        }
        // 4. Toss 결제 승인 요청 및 응답 검증
        ConfirmTossPaymentResponse response = tossPayment.confirmPayment(request.toPayload());
        PaymentJpaEntity paymentEntity = response.toPaymentJpaEntity(request.applicationId(),
                applicationCount);

        if (!paymentEntity.getPaymentStatus().isPaySuccess()) {
            throw new IllegalArgumentException("결제가 실패하였습니다.");
        }

        // 5. 결제 정보 저장
        paymentRepository.save(paymentEntity);
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
}
