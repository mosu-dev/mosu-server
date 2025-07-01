package life.mosu.mosuserver.presentation.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import life.mosu.mosuserver.presentation.oauth.dto.AuthRequiredResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ResponseEntityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException authException
    ) throws IOException {
        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        final String message = "로그인이 필요합니다.";

        final ResponseEntity<AuthRequiredResponse> responseEntity = ResponseEntity
            .status(status)
            .body(new AuthRequiredResponse(message));
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final HttpHeaders headers = responseEntity.getHeaders();
        headers.forEach((key, value) -> {
            final String headerValue = String.join(",", value);
            response.addHeader(key, headerValue);
        });

        try (
            final OutputStreamWriter writer = new OutputStreamWriter(
                response.getOutputStream(),
                StandardCharsets.UTF_8
            )
        ) {
            objectMapper.writeValue(writer, responseEntity.getBody());
        }
    }
}