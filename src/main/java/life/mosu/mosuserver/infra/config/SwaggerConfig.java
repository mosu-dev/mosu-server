package life.mosu.mosuserver.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MOSU API 문서")
                        .version("1.0.0")
                ).servers(List.of(
                        new Server().url("https://api.mosuedu.com/api/v1")
                                .description("MOSU SERVER")
                ));
    }
}