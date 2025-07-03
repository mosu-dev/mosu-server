package life.mosu.mosuserver.payment;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import life.mosu.mosuserver.infra.payment.TossPaymentClient;
import life.mosu.mosuserver.infra.payment.dto.ConfirmTossPaymentResponse;
import life.mosu.mosuserver.infra.payment.dto.TossPaymentPayload;
import life.mosu.mosuserver.payment.stub.ConfirmFakeRestOperationsStub;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestOperations;

@Slf4j
public class TossPaymentClientTest {
    private static TossPaymentClient tossPaymentClient;

    @Nested
    static class 성공응답 {
        @BeforeAll
        static void beforeAll() {
            ConfirmTossPaymentResponse dummyResponse = createDummySuccessResponse();
            RestOperations restOperations = new ConfirmFakeRestOperationsStub(dummyResponse);
            tossPaymentClient = new TossPaymentClient(restOperations, "http://baseUrl.com");
        }

        @Test
        void 성공시_변환_된_응답에_NULL이_포함되면_안됩니다() {
            TossPaymentPayload payload = new TossPaymentPayload("test", "test", 1000L);
            ConfirmTossPaymentResponse response = tossPaymentClient.confirmPayment(payload);

            assertNotNull(response, "응답 객체는 null이 아니어야 합니다");

            assertAll("null 체크",
                    () -> assertNotNull(response.getPaymentKey(), "paymentKey는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getOrderId(), "orderId는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getStatus(), "status는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getApprovedAt(), "approvedAt는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getTotalAmount(), "totalAmount는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getBalanceAmount(), "balanceAmount는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getSuppliedAmount(),
                            "suppliedAmount는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getVat(), "vat는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getTaxFreeAmount(), "taxFreeAmount는 null이면 안 됩니다"),
                    () -> assertNotNull(response.getMethod(), "method는 null이면 안 됩니다")
            );
        }

        private static ConfirmTossPaymentResponse createDummySuccessResponse() {
            return new ConfirmTossPaymentResponse(
                    "tviva20250702231345mODA4",     // paymentKey
                    "IDMAoki7azYp8SzQ06LMt12323235", // orderId
                    "DONE",                          // status
                    "2025-07-02T23:14:33+09:00",    // approvedAt
                    1_000,                           // totalAmount
                    1_000,                           // balanceAmount
                    1_000,                           // suppliedAmount
                    1_000,                           // vat
                    1_000,                           // taxFreeAmount
                    "간편결제"                            // method
            );
        }

    }
//    private static TossPaymentResponse createDummyFailureResponse() {
//        return new TossPaymentFailureResponse();
//    }

    static class TossPaymentFailureResponse{
        private String code;
        private String message;
    }

}

