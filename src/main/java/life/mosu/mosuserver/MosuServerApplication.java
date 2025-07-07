package life.mosu.mosuserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MosuServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MosuServerApplication.class, args);
    }

}
