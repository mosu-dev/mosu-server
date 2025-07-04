package life.mosu.mosuserver.application.auth;

import org.springframework.stereotype.Service;

@Service
public class DanalVerificationService implements VerificationService {
    //TODO: Danal 인증 서비스 구현
    @Override
    public boolean verify(final String verificationCode, final String phoneNumber) {
        return true;
    }
}