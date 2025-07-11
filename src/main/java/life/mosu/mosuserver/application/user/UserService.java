package life.mosu.mosuserver.application.user;

import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.domain.user.UserJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;

    public String getCustomerKey(Long userId) {
        return userJpaRepository.findById(userId)
                .map(UserJpaEntity::getCustomerKey)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
    }

}
