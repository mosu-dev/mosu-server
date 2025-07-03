package life.mosu.mosuserver.payment.stub;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class BaseFakeRestOperationsStub extends RestTemplate {
    @Override
    public <T> ResponseEntity<T> exchange(String url,
            HttpMethod method,
            HttpEntity<?> requestEntity,
            Class<T> responseType,
            Object... uriVariables) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
