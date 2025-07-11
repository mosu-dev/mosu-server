package life.mosu.mosuserver.global.util;

import java.util.UUID;

public class KeyGeneratorUtil {

    public static String generateUUIDCustomerKey() {
        return UUID.randomUUID().toString();
    }
}
