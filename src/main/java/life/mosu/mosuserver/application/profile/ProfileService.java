package life.mosu.mosuserver.application.profile;

import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;
import life.mosu.mosuserver.domain.profile.ProfileJpaRepository;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import life.mosu.mosuserver.presentation.profile.dto.EditProfileRequest;
import life.mosu.mosuserver.presentation.profile.dto.ProfileDetailResponse;
import life.mosu.mosuserver.presentation.profile.dto.ProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileJpaRepository profileJpaRepository;

    @Transactional
    public void registerProfile(Long userId, ProfileRequest request) {
        if (profileJpaRepository.existsByUserId(userId)) {
            throw new CustomRuntimeException(ErrorCode.PROFILE_ALREADY_EXISTS, userId);
        }

        ProfileJpaEntity profile = request.toEntity(userId);
        profileJpaRepository.save(profile);
    }


    @Transactional
    public void editProfile(Long userId, EditProfileRequest request) {
        ProfileJpaEntity profile = profileJpaRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PROFILE_NOT_FOUND, userId));

        profile.edit(request);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProfileDetailResponse getProfile(Long userId) {
        ProfileJpaEntity profile = profileJpaRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.PROFILE_DOES_NOT_EXIST, userId));

        return ProfileDetailResponse.from(profile);
    }
}

