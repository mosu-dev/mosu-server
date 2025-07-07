package life.mosu.mosuserver.infra.property;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "aws.s3")
@Slf4j
public class S3Properties {

    private final int presignedUrlExpirationMinutes;

    @PostConstruct
    public void init() {
        log.info("S3 Properties Loaded. Expiration Time: {}", presignedUrlExpirationMinutes);
    }
}