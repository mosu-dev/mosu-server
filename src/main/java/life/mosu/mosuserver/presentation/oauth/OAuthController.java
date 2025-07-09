package life.mosu.mosuserver.presentation.oauth;

import static life.mosu.mosuserver.global.resolver.AuthorizationRequestRedirectResolver.REDIRECT_PARAM_KEY;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    @GetMapping("/login/{registrationId}")
    public RedirectView login(
            @PathVariable String registrationId,
            @RequestParam(REDIRECT_PARAM_KEY) String redirect
    ) {
        final String url = UriComponentsBuilder
                .fromPath("/api/v1/oauth2/authorization/{registrationId}")
                .queryParam(REDIRECT_PARAM_KEY, redirect)
                .buildAndExpand(registrationId)
                .toUriString();
        return new RedirectView(url);
    }
}