package life.mosu.mosuserver.presentation.payment;

import life.mosu.mosuserver.application.payment.PaymentService;
import life.mosu.mosuserver.global.util.ApiResponseWrapper;
import life.mosu.mosuserver.presentation.payment.dto.CancelPaymentRequest;
import life.mosu.mosuserver.presentation.payment.dto.PaymentPrepareResponse;
import life.mosu.mosuserver.presentation.payment.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentWidgetController {
    private final PaymentService paymentService;
    //TODO: 신청서 필요함.
    @PostMapping("/prepare")
    public ApiResponseWrapper<PaymentPrepareResponse> prepare(){
        PaymentPrepareResponse response = paymentService.prepare();
        return ApiResponseWrapper.success(HttpStatus.OK,"결제 시작", response);
    }
    /**
     * 할인 금액 재계산 검증 로직 필요 -> 실패하면, 해당 부분에서 바로 실패가 떠야함
     * @param request
     * @return
     */
    @PostMapping("/confirm")
    public ApiResponseWrapper<Void> confirm(@RequestBody PaymentRequest request){
        paymentService.confirm(request);
        return ApiResponseWrapper.success(HttpStatus.CREATED, "결제 승인 성공");
    }

    @PostMapping("/{paymentId}/cancel")
    public ApiResponseWrapper<Void> cancel(
            @PathVariable String paymentId,
            @RequestBody CancelPaymentRequest request
    ){
        paymentService.cancel(paymentId, request);
        return ApiResponseWrapper.success(HttpStatus.OK, "결제 취소 성공");
    }
}
