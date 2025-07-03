package life.mosu.mosuserver.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import life.mosu.mosuserver.infra.payment.application.TossPaymentErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TossPaymentConfig {
    @Value("${toss.secret-key}")
    private String secretKey;

    @Bean
    public RestOperations tossPaymentRestTemplate(TossPaymentErrorHandler tossPaymentErrorHandler){
        RestTemplate restTemplate = new RestTemplate();

        String encoded = Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

        restTemplate.setInterceptors(List.of((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, "Basic " + encoded);
            return execution.execute(request, body);
        }));
        restTemplate.setErrorHandler(tossPaymentErrorHandler);
        return restTemplate;
    }
}
