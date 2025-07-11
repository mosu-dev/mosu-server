package life.mosu.mosuserver.global.initializer;

import static life.mosu.mosuserver.domain.user.UserRole.ROLE_ADMIN;
import static life.mosu.mosuserver.domain.user.UserRole.ROLE_USER;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;
import life.mosu.mosuserver.domain.profile.ProfileJpaRepository;
import life.mosu.mosuserver.domain.profile.SchoolInfoJpaVO;
import life.mosu.mosuserver.domain.user.UserJpaEntity;
import life.mosu.mosuserver.domain.user.UserJpaRepository;
import life.mosu.mosuserver.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAndProfileInitializer {

    private final UserJpaRepository userRepository;
    private final ProfileJpaRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.count() > 0 || profileRepository.count() > 0) {
            log.info("이미 더미 데이터가 존재하여 초기화를 건너뜝니다.");
            return;
        }

        List<UserJpaEntity> createdUsers = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            String loginId = "user" + i;
            String name = (i % 2 == 0) ? "김철수" + i : "이영희" + i;
            Gender gender = (i % 2 == 0) ? Gender.MALE : Gender.FEMALE;
            LocalDate birth = LocalDate.of(1990 + (i % 5), (i % 12) + 1, (i % 28) + 1);
            boolean agreedToMarketing = random.nextBoolean();
            UserRole userRole = (i == 1) ? ROLE_ADMIN : ROLE_USER;

            UserJpaEntity user = UserJpaEntity.builder()
                    .loginId(loginId)
                    .password(passwordEncoder.encode("password" + i + "!"))
                    .gender(gender)
                    .name(name)
                    .birth(birth)
                    .agreedToTermsOfService(true)
                    .agreedToPrivacyPolicy(true)
                    .agreedToMarketing(agreedToMarketing)
                    .userRole(userRole)
                    .build();

            createdUsers.add(userRepository.save(user));

            String phoneNumber =
                    "010-" + String.format("%04d", i) + "-" + String.format("%04d", i + 1000);
            String email = "user" + i + "@example.com";
            Education education = Education.values()[random.nextInt(Education.values().length)];
            Grade grade = Grade.values()[random.nextInt(Grade.values().length)];
            SchoolInfoJpaVO schoolInfo = new SchoolInfoJpaVO(("모수대학교" + (i % 3 + 1)), "123-23",
                    "서울시 모수구 모수동");
            String recommenderPhoneNumber = (i % 3 == 0) ? "010-1234-5678" : null;

            ProfileJpaEntity profile = ProfileJpaEntity.builder()
                    .userId(user.getId())
                    .userName(user.getName())
                    .gender(user.getGender())
                    .birth(user.getBirth())
                    .phoneNumber(phoneNumber)
                    .email(email)
                    .education(education)
                    .schoolInfo(schoolInfo)
                    .grade(grade)
                    .build();

            profile.registerRecommenderPhoneNumber(recommenderPhoneNumber);

            profileRepository.save(profile);
        }
    }
}