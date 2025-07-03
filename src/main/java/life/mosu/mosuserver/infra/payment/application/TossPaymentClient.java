package life.mosu.mosuserver.infra.payment.application;

import life.mosu.mosuserver.infra.payment.application.dto.CancelTossPaymentResponse;
import life.mosu.mosuserver.infra.payment.application.dto.ConfirmTossPaymentResponse;
import life.mosu.mosuserver.infra.payment.application.dto.TossPaymentPayload;
import life.mosu.mosuserver.presentation.payment.dto.CancelPaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class TossPaymentClient {

    private final String tossPaymentBaseUrl;
    private final RestOperations restTemplate;

    public TossPaymentClient(
            RestOperations restTemplate,
            @Value("${toss.api.base-url:https://default.url.com}")
            String tossPaymentBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.tossPaymentBaseUrl = tossPaymentBaseUrl;
    }
    public ConfirmTossPaymentResponse confirmPayment(TossPaymentPayload request) {
        String uri = UriComponentsBuilder.fromUriString(tossPaymentBaseUrl)
                .path("/confirm")
                .toUriString();

        return postToToss(
                uri,
                request,
                ConfirmTossPaymentResponse.class,
                "[TOSS_CONFIRM]"
        );
    }

    public CancelTossPaymentResponse cancelPayment(String paymentKey, CancelPaymentRequest request) {
        String uri = UriComponentsBuilder.fromUriString(tossPaymentBaseUrl)
                .pathSegment(paymentKey, "cancel")
                .toUriString();

        return postToToss(
                uri,
                request,
                CancelTossPaymentResponse.class,
                "[TOSS_CANCEL]"
        );
    }

    private <T, R> R postToToss(String uri, T body, Class<R> responseType, String logPrefix) {
        try {
            HttpEntity<T> entity = new HttpEntity<>(body);
            ResponseEntity<R> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    entity,
                    responseType
            );
            log.info("{} success: {}", logPrefix, response.getBody());
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("{} failed: {}", logPrefix, ex.getResponseBodyAsString(), ex);
            throw ex; // 혹은 throw new TossPaymentException(...)
        }
    }
}
