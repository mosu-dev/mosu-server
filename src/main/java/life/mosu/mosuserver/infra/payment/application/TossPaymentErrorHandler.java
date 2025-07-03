package life.mosu.mosuserver.infra.payment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import life.mosu.mosuserver.infra.payment.application.dto.TossPaymentErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
@RequiredArgsConstructor
public class TossPaymentErrorHandler implements ResponseErrorHandler {
    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError()
                   || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response)
            throws IOException {

        String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
        HttpStatus status;

        try {
            status = HttpStatus.valueOf(response.getStatusCode().value());
        } catch (IllegalArgumentException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        try {
            TossPaymentErrorResponse error = objectMapper.readValue(body, TossPaymentErrorResponse.class);

            throw new RuntimeException();

        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

}
