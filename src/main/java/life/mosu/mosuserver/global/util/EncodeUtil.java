package life.mosu.mosuserver.global.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodeUtil {
    public static String passwordEncode(final PasswordEncoder encoder, final String password) {
        return encoder.encode(password);
    }
}
