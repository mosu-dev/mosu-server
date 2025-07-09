package life.mosu.mosuserver.global.config;

import java.util.Arrays;
import java.util.List;
import life.mosu.mosuserver.application.oauth.OAuthUserService;
import life.mosu.mosuserver.global.handler.OAuth2LoginFailureHandler;
import life.mosu.mosuserver.global.handler.OAuth2LoginSuccessHandler;
import life.mosu.mosuserver.global.resolver.AuthorizationRequestRedirectResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final List<String> clients = List.of(
            "http://localhost:3000",
            "http://localhost:8080",
            "https://mosuedu.com",
            "http://api.mosuedu.com",
            "https://api.mosuedu.com"
    );
    private final OAuthUserService userService;
    private final OAuth2LoginSuccessHandler loginSuccessHandler;
    private final OAuth2LoginFailureHandler loginFailureHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AuthorizationRequestRedirectResolver authorizationRequestRedirectResolver;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(
                        "/favicon.ico",
                        "/error",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(FrameOptionsConfig::disable))
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(
//                                "/api/v1/profile/**",
//                                "/api/v1/admin/**"
//                        )
//                        .hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2.redirectionEndpoint(redirection ->
                                redirection.baseUri("/oauth2/callback/{registrationId}"))
                        .loginProcessingUrl("/oauth2/login")
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.userService(userService)
                        )
                        .authorizationEndpoint(authorization ->
                                authorization.authorizationRequestResolver(
                                        authorizationRequestRedirectResolver)
                        )
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                )
                .logout(config -> config.logoutSuccessUrl("/"))
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