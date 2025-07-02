package life.mosu.mosuserver.global.config;

import life.mosu.mosuserver.applicaiton.oauth.OAuthUserService;
import life.mosu.mosuserver.global.handler.OAuth2LoginSuccessHandler;
import life.mosu.mosuserver.presentation.oauth.AccessTokenFilter;
import life.mosu.mosuserver.presentation.oauth.AuthorizationRequestRedirectResolver;
import life.mosu.mosuserver.presentation.oauth.TokenExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthUserService userService;
    private final OAuth2LoginSuccessHandler loginSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthorizationRequestRedirectResolver authorizationRequestRedirectResolver;
    private final AccessTokenFilter accessTokenFilter;
    private final TokenExceptionFilter tokenExceptionFilter;

    public static final List<String> clients = List.of(
        "http://localhost:3000",
        "http://localhost:8080",
        "https://mosuedu.com"
    );

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/oauth2/**",
                    "/api/v1/auth/**",
                    "/api/v1/login/**"
                )
                .permitAll()
                .requestMatchers(
                    "/api/v1/admin/**"
                )
                .hasRole("ADMIN")
                .anyRequest()
                .hasAnyRole("USER")
            )
            .oauth2Login(oauth2 -> oauth2.redirectionEndpoint(redirection ->
                    redirection.baseUri("/login/oauth2/code/{registrationId}"))
                .userInfoEndpoint(userInfoEndpoint ->
                    userInfoEndpoint.userService(userService)
                )
                .loginProcessingUrl("/auth/login")
                .authorizationEndpoint(authorization ->
                    authorization.authorizationRequestResolver(authorizationRequestRedirectResolver)
                )
                .successHandler(loginSuccessHandler))
            .addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(tokenExceptionFilter, accessTokenFilter.getClass())
            .exceptionHandling(exceptions ->
                exceptions.authenticationEntryPoint(authenticationEntryPoint));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(clients);
        configuration.setAllowedMethods(Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
        ));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}