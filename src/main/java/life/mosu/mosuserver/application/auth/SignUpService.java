package life.mosu.mosuserver.application.auth;

import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.domain.user.UserJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.auth.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;

    @Transactional
    public void signUp(final SignUpRequest request) {

        if (userRepository.existsByLoginId(request.id())) {
            throw new CustomRuntimeException(ErrorCode.USER_ALREADY_EXISTS);
        }

        UserJpaEntity user = request.toAuthEntity(passwordEncoder);

        userRepository.save(user);
    }
}