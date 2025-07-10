package life.mosu.mosuserver.application.profile;

import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;
import life.mosu.mosuserver.domain.profile.ProfileJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.profile.dto.RecommenderRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommenderService {

    private final ProfileJpaRepository profileJpaRepository;

    @Transactional
    public void registerRecommender(Long userId, RecommenderRegistrationRequest request) {
        ProfileJpaEntity profile = profileJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PROFILE_NOT_FOUND));
        if (profile.getRecommenderPhoneNumber() != null) {
            throw new CustomRuntimeException(ErrorCode.ALREADY_REGISTERED_RECOMMENDER);
        }

        profile.registerRecommenderPhoneNumber(request.phoneNumber());
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Boolean verifyRecommender(Long userId) {
        ProfileJpaEntity profile = profileJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PROFILE_NOT_FOUND));
        return profile.getRecommenderPhoneNumber() != null;
    }

}
