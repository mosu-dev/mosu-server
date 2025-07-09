package life.mosu.mosuserver.presentation.oauth;

import static life.mosu.mosuserver.global.resolver.AuthorizationRequestRedirectResolver.REDIRECT_PARAM_KEY;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Tag(description = "OAuth 관련 API", name = "OAuth")
public interface OAuthControllerDocs {

    @Operation(summary = "OAuth 로그인 리다이렉트")
    public RedirectView login(
            @PathVariable String registrationId,
            @RequestParam(REDIRECT_PARAM_KEY) String redirect
    );
}
