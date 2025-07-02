package life.mosu.mosuserver.presentation.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import static life.mosu.mosuserver.presentation.oauth.AuthorizationRequestRedirectResolver.REDIRECT_PARAM_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class OAuthController {

    @GetMapping("/login/{registrationId}")
    public RedirectView login(@PathVariable String registrationId, @RequestParam(REDIRECT_PARAM_KEY) String redirect) {
        final String url = UriComponentsBuilder
            .fromPath("/oauth2/authorization/{registrationId}")
            .queryParam(REDIRECT_PARAM_KEY, redirect)
            .buildAndExpand(registrationId)
            .toUriString();
        return new RedirectView(url);
    }
}