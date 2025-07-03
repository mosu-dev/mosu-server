package life.mosu.mosuserver.application.payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import life.mosu.mosuserver.domain.discount.DiscountPolicy;
import life.mosu.mosuserver.domain.payment.PaymentJpaEntity;
import life.mosu.mosuserver.domain.payment.PaymentRepository;
import life.mosu.mosuserver.infra.payment.TossPaymentClient;
import life.mosu.mosuserver.infra.payment.dto.ConfirmTossPaymentResponse;
import life.mosu.mosuserver.presentation.payment.dto.CancelPaymentRequest;
import life.mosu.mosuserver.presentation.payment.dto.PaymentPrepareResponse;
import life.mosu.mosuserver.presentation.payment.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 영속화 처리
 * 이미 들어올 때 할인 정책을 포함해야함
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossPaymentClient tossPayment;
    private final PaymentRepository paymentRepository;

    //TODO : 정책 PR 에서 해당 부분 수정예정
    public PaymentPrepareResponse prepare() {
        String uuid = generateOrderId();
        //application 에서 조회
        int applicationCount = 3;

        int totalAmount = DiscountPolicy.QUANTITY_PERCENTAGE.calculateDiscount(applicationCount, 10_000);
        return PaymentPrepareResponse.of(uuid, totalAmount);
    }

    @Transactional
    public void confirm(PaymentRequest request){
        // 2. 신청 조회 (가정: applicationService 또는 repository 호출)
        int applicationCount = 3; // 나중에 실제 DB에서 조회
        // TODO: 신청 상태 검증 (결제 가능 상태인지)

        // 3. 금액 재계산 및 비교
        int totalAmount = DiscountPolicy.QUANTITY_PERCENTAGE.calculateDiscount(applicationCount, 10_000);
        if (request.amount() != totalAmount) {
            throw new IllegalArgumentException("결제 금액이 올바르지 않습니다.");
        }

        // 4. 중복 결제 여부 확인 (예: paymentRepository.existsByApplicationIdAndStatusPaid)
        // TODO: 중복 결제 로직 추가

        // 5. Toss 결제 승인 요청 및 응답 검증
        ConfirmTossPaymentResponse response = tossPayment.confirmPayment(request.toPayload());
        PaymentJpaEntity paymentEntity = response.toEntity(request.applicationId());

        if (!paymentEntity.getPaymentStatus().isPaySuccess()) {
            throw new IllegalArgumentException("결제가 실패하였습니다.");
        }

        // 6. 결제 정보 저장
        paymentRepository.save(paymentEntity);
    }

    @Transactional
    public void cancel(String paymentId, CancelPaymentRequest request){
        //환불이 가능한가?
        tossPayment.cancelPayment(paymentId, request);
        // 환불 정책
        // 영속화 해지할 필요 X
        // 영속화 된 거에서 환불 상태로 변경
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString();
    }
}
