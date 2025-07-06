package life.mosu.mosuserver.application.payment;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OrderIdGenerator {

    public String generate() {
        return UUID.randomUUID().toString();
    }
}
