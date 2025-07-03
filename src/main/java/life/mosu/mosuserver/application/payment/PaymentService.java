package life.mosu.mosuserver.application.payment;

import java.math.BigDecimal;
import java.util.UUID;
import life.mosu.mosuserver.domain.payment.PaymentJpaEntity;
import life.mosu.mosuserver.domain.payment.PaymentRepository;
import life.mosu.mosuserver.infra.payment.application.TossPaymentClient;
import life.mosu.mosuserver.infra.payment.application.dto.ConfirmTossPaymentResponse;
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
        BigDecimal totalPrice = BigDecimal.valueOf(1_000L);
        return PaymentPrepareResponse.of(uuid, totalPrice);
    }

    @Transactional
    public void confirm(PaymentRequest request){
        //totalPrice 를 재연산한 후 값이 같은지 확인
        ConfirmTossPaymentResponse response = tossPayment.confirmPayment(request.toPayload());
        PaymentJpaEntity paymentEntity = response.toEntity(request.applicationId());
        if(!paymentEntity.getPaymentStatus().isPaySuccess()){
            throw new IllegalArgumentException("결제가 실패하였습니다.");
        }
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
