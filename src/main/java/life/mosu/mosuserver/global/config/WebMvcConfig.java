package life.mosu.mosuserver.global.config;

import life.mosu.mosuserver.global.resolver.UserIdArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final UserIdArgumentResolver resolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
