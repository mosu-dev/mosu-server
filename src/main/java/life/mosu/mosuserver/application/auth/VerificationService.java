package life.mosu.mosuserver.application.auth;

public interface VerificationService {
    boolean verify(String verificationCode, String phoneNumber);
}
