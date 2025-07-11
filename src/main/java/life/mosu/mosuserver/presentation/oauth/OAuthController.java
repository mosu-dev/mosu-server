package life.mosu.mosuserver.presentation.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    @GetMapping("/authorize-code")
    public ResponseEntity<Void> handleOAuthCodeFromFrontend(
            @RequestParam String code,
            @RequestParam(required = false) String state
    ) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromPath("/api/v1/oauth2/callback/kakao")
                .queryParam("code", code);

        if (state != null && !state.isEmpty()) {
            uriBuilder.queryParam("state", state);
        }

        String redirectUrl = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path(uriBuilder.toUriString())
                .build()
                .toUriString();

        log.info("클라이언트를 Spring Security OAuth2 콜백 URL로 리다이렉트합니다: {}", redirectUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, redirectUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}