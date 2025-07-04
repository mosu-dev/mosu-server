package life.mosu.mosuserver.application.auth;

import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.domain.user.UserJpaRepository;
import life.mosu.mosuserver.domain.user.UserRole;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.auth.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static life.mosu.mosuserver.global.util.EncodeUtil.passwordEncode;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@RequiredArgsConstructor
public class SignUpService {

    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;

    @Transactional
    public void signUp(final SignUpRequest request) {

        if (userRepository.existsByLoginId(request.id())) {
            throw new CustomRuntimeException(ErrorCode.USER_ALREADY_EXISTS);
        }

        UserJpaEntity user = UserJpaEntity.builder()
            .loginId(request.id())
            .password(passwordEncode(passwordEncoder, request.password()))
            .userRole(UserRole.ROLE_PENDING)
            .build();

        userRepository.save(user);
    }
}