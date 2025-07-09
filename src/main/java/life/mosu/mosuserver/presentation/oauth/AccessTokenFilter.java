//package life.mosu.mosuserver.presentation.oauth;
//
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import life.mosu.mosuserver.application.auth.AccessTokenService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//@RequiredArgsConstructor
//public class AccessTokenFilter extends OncePerRequestFilter {
//
//    private static final String TOKEN_PREFIX = "Bearer ";
//    private final AccessTokenService accessTokenService;
//
//    @Value("${endpoints.reissue}")
//    private String reissueEndpoint;
//
//    @Override
//    protected void doFilterInternal(
//            final HttpServletRequest request,
//            final HttpServletResponse response,
//            final FilterChain filterChain
//    ) throws ServletException, IOException {
//        if (request.getRequestURI().equals(reissueEndpoint)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if (request.getRequestURI().startsWith("/api/v1/oauth2")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        final String accessToken = resolveToken(request);
//        if (accessToken != null) {
//            setAuthentication(accessToken);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private void setAuthentication(final String accessToken) {
//        final Authentication authentication = accessTokenService.getAuthentication(accessToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    private String resolveToken(final HttpServletRequest request) {
//        final String token = request.getHeader(AUTHORIZATION);
//        if (ObjectUtils.isEmpty(token) || !token.startsWith(TOKEN_PREFIX)) {
//            return null;
//        }
//        return token.substring(TOKEN_PREFIX.length());
//    }
//}