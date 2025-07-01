package life.mosu.mosuserver.presentation.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import static life.mosu.mosuserver.presentation.oauth.AuthorizationRequestRedirectResolver.REDIRECT_PARAM_KEY;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class OAuthController {

    @GetMapping("/login/{registrationId}")
    public RedirectView login(@PathVariable String registrationId, @RequestParam(REDIRECT_PARAM_KEY) String redirect) {
        final String url = String.format(
            "/oauth2/authorization/%s?%s=%s",
            registrationId,
            REDIRECT_PARAM_KEY,
            redirect
        );
        return new RedirectView(url);
    }
}