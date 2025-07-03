package life.mosu.mosuserver.payment.stub;

import life.mosu.mosuserver.infra.payment.dto.ConfirmTossPaymentResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class ConfirmFakeRestOperationsStub extends BaseFakeRestOperationsStub {
    private final ConfirmTossPaymentResponse response;

    public ConfirmFakeRestOperationsStub(ConfirmTossPaymentResponse response) {
        this.response = response;
    }

    @Override
    public <T> ResponseEntity<T> exchange(String url,
            HttpMethod method,
            HttpEntity<?> requestEntity,
            Class<T> responseType,
            Object... uriVariables) {

        if (!url.contains("/confirm")) {
            throw new IllegalArgumentException("ConfirmFakeRestOperations can only handle /confirm");
        }
        return ResponseEntity.ok(responseType.cast(response));
    }
}

