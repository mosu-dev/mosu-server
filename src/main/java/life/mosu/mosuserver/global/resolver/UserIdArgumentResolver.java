package life.mosu.mosuserver.global.resolver;

import life.mosu.mosuserver.application.auth.AccessTokenService;
import life.mosu.mosuserver.application.auth.PrincipalDetails;
import life.mosu.mosuserver.global.annotation.UserId;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final AccessTokenService accessTokenService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) && parameter.getParameterType()
                .equals(Long.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
            final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !(authentication.getPrincipal() instanceof PrincipalDetails)) {
            throw new CustomRuntimeException(ErrorCode.PRINCIPAL_NOT_FOUND);
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        return principal.getId();
    }
}
