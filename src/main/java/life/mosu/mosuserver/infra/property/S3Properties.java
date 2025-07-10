package life.mosu.mosuserver.infra.property;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aws.s3")
@Slf4j
public class S3Properties {

    private int presignedUrlExpirationMinutes;

    @PostConstruct
    public void init() {
        log.info("S3 Properties Loaded. Expiration Time: {}", presignedUrlExpirationMinutes);
    }
}