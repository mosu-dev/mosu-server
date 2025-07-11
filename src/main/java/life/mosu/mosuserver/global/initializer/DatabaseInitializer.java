package life.mosu.mosuserver.global.initializer;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import life.mosu.mosuserver.domain.application.ApplicationJpaEntity;
import life.mosu.mosuserver.domain.application.ApplicationJpaRepository;
import life.mosu.mosuserver.domain.application.Lunch;
import life.mosu.mosuserver.domain.application.Subject;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.applicationschool.ApplicationSchoolJpaRepository;
import life.mosu.mosuserver.domain.event.DurationJpaVO;
import life.mosu.mosuserver.domain.event.EventJpaEntity;
import life.mosu.mosuserver.domain.event.EventJpaRepository;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaEntity;
import life.mosu.mosuserver.domain.inquiry.InquiryJpaRepository;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaEntity;
import life.mosu.mosuserver.domain.inquiryAnswer.InquiryAnswerJpaRepository;
import life.mosu.mosuserver.domain.notice.NoticeJpaEntity;
import life.mosu.mosuserver.domain.notice.NoticeJpaRepository;
import life.mosu.mosuserver.domain.profile.Education;
import life.mosu.mosuserver.domain.profile.Gender;
import life.mosu.mosuserver.domain.profile.Grade;
import life.mosu.mosuserver.domain.profile.ProfileJpaEntity;
import life.mosu.mosuserver.domain.profile.ProfileJpaRepository;
import life.mosu.mosuserver.domain.profile.SchoolInfoJpaVO;
import life.mosu.mosuserver.domain.school.AddressJpaVO;
import life.mosu.mosuserver.domain.school.Area;
import life.mosu.mosuserver.domain.school.SchoolJpaEntity;
import life.mosu.mosuserver.domain.school.SchoolJpaRepository;
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
public class DatabaseInitializer {

    private final UserJpaRepository userRepository;
    private final ProfileJpaRepository profileRepository;
    private final SchoolJpaRepository schoolRepository;
    private final ApplicationJpaRepository applicationRepository;
    private final ApplicationSchoolJpaRepository applicationSchoolRepository;
    private final InquiryJpaRepository inquiryJpaRepository;
    private final NoticeJpaRepository noticeJpaRepository;
    private final InquiryAnswerJpaRepository inquiryAnswerJpaRepository;
    private final EventJpaRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.count() > 0 || schoolRepository.count() > 0) {
            log.info("이미 더미 데이터가 존재하여 전체 초기화를 건너뜁니다.");
            return;
        }

        log.info("전체 더미 데이터 초기화를 시작합니다...");
        Random random = new Random();

        List<UserJpaEntity> createdUsers = initializeUsersAndProfiles(random);
        List<SchoolJpaEntity> createdSchools = initializeSchools();
        initializeApplications(createdUsers, createdSchools, random);
        initializeBoardItems(createdUsers, random);

        log.info("모든 더미 데이터 초기화가 완료되었습니다.");
    }

    private List<UserJpaEntity> initializeUsersAndProfiles(Random random) {
        List<UserJpaEntity> createdUsers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserJpaEntity user = UserJpaEntity.builder()
                    .loginId("user" + i)
                    .password(passwordEncoder.encode("password" + i + "!"))
                    .gender((i % 2 == 0) ? Gender.MALE : Gender.FEMALE)
                    .name((i % 2 == 0) ? "김철수" + i : "이영희" + i)
                    .birth(LocalDate.of(1990 + (i % 5), (i % 12) + 1, (i % 28) + 1))
                    .agreedToTermsOfService(true)
                    .agreedToPrivacyPolicy(true)
                    .agreedToMarketing(random.nextBoolean())
                    .userRole((i == 1) ? UserRole.ROLE_ADMIN : UserRole.ROLE_USER)
                    .build();
            userRepository.save(user);
            createdUsers.add(user);

            ProfileJpaEntity profile = ProfileJpaEntity.builder()
                    .userId(user.getId())
                    .userName(user.getName())
                    .gender(user.getGender())
                    .birth(user.getBirth())
                    .phoneNumber("010-" + String.format("%04d", i) + "-" + String.format("%04d",
                            i + 1000))
                    .email("user" + i + "@example.com")
                    .education(Education.values()[random.nextInt(Education.values().length)])
                    .schoolInfo(new SchoolInfoJpaVO("모수대학교" + (i % 3 + 1), "123-23", "서울시 모수구 모수동"))
                    .grade(Grade.values()[random.nextInt(Grade.values().length)])
                    .build();

            profile.registerRecommenderPhoneNumber((i % 3 == 0) ? "010-1234-5678" : null);
            profileRepository.save(profile);
        }
        log.info("User 및 Profile 데이터 {}건 생성 완료.", createdUsers.size());
        return createdUsers;
    }

    private List<SchoolJpaEntity> initializeSchools() {
        List<SchoolJpaEntity> schools = new ArrayList<>(List.of(
                SchoolJpaEntity.builder()
                        .schoolName("모수고등학교")
                        .area(Area.DAECHI)
                        .address(new AddressJpaVO("06164", "서울특별시", "강남구 테헤란로 123"))
                        .examDate(LocalDate.of(2025, 11, 20))
                        .capacity(300L)
                        .build(),
                SchoolJpaEntity.builder()
                        .schoolName("대치고등학교")
                        .area(Area.DAECHI)
                        .address(new AddressJpaVO("06283", "서울특별시", "강남구 대치동 학원가 100"))
                        .examDate(LocalDate.of(2025, 12, 5))
                        .capacity(150L)
                        .build(),
                SchoolJpaEntity.builder()
                        .schoolName("부산명문고")
                        .area(Area.DAECHI)
                        .address(new AddressJpaVO("48057", "부산광역시", "해운대구 센텀시티로 50"))
                        .examDate(LocalDate.of(2025, 11, 25))
                        .capacity(250L)
                        .build(),
                SchoolJpaEntity.builder()
                        .schoolName("노원스터디센터")
                        .area(Area.NOWON)
                        .address(new AddressJpaVO("01777", "서울특별시", "노원구 동일로 1400"))
                        .examDate(LocalDate.of(2026, 1, 10))
                        .capacity(80L)
                        .build()
        ));
        schoolRepository.saveAll(schools);
        log.info("School 데이터 {}건 생성 완료.", schools.size());
        return schools;
    }

    private void initializeApplications(List<UserJpaEntity> users, List<SchoolJpaEntity> schools,
            Random random) {
        for (int i = 0; i < users.size(); i++) {
            UserJpaEntity user = users.get(i);
            ApplicationJpaEntity application = applicationRepository.save(
                    ApplicationJpaEntity.builder()
                            .userId(user.getId())
                            .guardianPhoneNumber("010-9876-" + String.format("%04d", 1000 + i))
                            .agreedToNotices(true)
                            .agreedToRefundPolicy(true)
                            .build()
            );

            Collections.shuffle(schools);
            int schoolsToApply = random.nextInt(2) + 2;

            for (int j = 0; j < Math.min(schoolsToApply, schools.size()); j++) {
                SchoolJpaEntity school = schools.get(j);
                Set<Subject> subjects = new HashSet<>();
                subjects.add(Subject.values()[random.nextInt(Subject.values().length)]);
                if (random.nextBoolean()) {
                    subjects.add(Subject.values()[random.nextInt(Subject.values().length)]);
                }

                applicationSchoolRepository.save(
                        ApplicationSchoolJpaEntity.builder()
                                .userId(user.getId())
                                .applicationId(application.getId())
                                .schoolId(school.getId())
                                .schoolName(school.getSchoolName())
                                .area(school.getArea())
                                .address(school.getAddress())
                                .examDate(LocalDate.of(2025, 10, 20 + i))
                                .lunch(Lunch.values()[random.nextInt(Lunch.values().length)])
                                .examinationNumber(
                                        String.format("EXAM-%d-%d", user.getId(), school.getId()))
                                .subjects(subjects)
                                .build()
                );
            }
        }
        log.info("Application 및 ApplicationSchool 데이터 생성 완료.");
    }

    private void initializeBoardItems(List<UserJpaEntity> users, Random random) {
        for (int i = 1; i <= 10; i++) {
            UserJpaEntity author = users.get(random.nextInt(users.size()));
            noticeJpaRepository.save(NoticeJpaEntity.builder()
                    .title("공지사항 제목 " + i)
                    .content("이것은 " + i + "번째 공지사항의 내용입니다.")
                    .userId(author.getId())
                    .author(author.getName())
                    .build());
        }

        List<InquiryJpaEntity> inquiries = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserJpaEntity author = users.get(random.nextInt(users.size()));
            inquiries.add(inquiryJpaRepository.save(
                    InquiryJpaEntity.builder()
                            .title("문의 제목 " + i)
                            .content("안녕하세요. " + i + "번째 문의 내용입니다.")
                            .userId(author.getId())
                            .author(author.getName())
                            .build())
            );
        }

        for (int i = 1; i <= 10; i++) {
            InquiryJpaEntity inquiryToAnswer = inquiries.get(random.nextInt(inquiries.size()));

            UserJpaEntity answerer = users.get(random.nextInt(users.size()));
            inquiryAnswerJpaRepository.save(InquiryAnswerJpaEntity.builder()
                    .title("Re: " + inquiryToAnswer.getTitle())
                    .content("문의하신 내용에 대한 답변입니다. " + i + "번째 답변입니다.")
                    .inquiryId(inquiryToAnswer.getId())
                    .userId(answerer.getId())
                    .build());
            inquiryToAnswer.updateStatusToComplete();
            inquiryJpaRepository.save(inquiryToAnswer);
        }

        for (int i = 1; i <= 10; i++) {
            LocalDate startDate = LocalDate.now().plusDays(i * 2);
            LocalDate endDate = startDate.plusDays(random.nextInt(7) + 3);
            eventRepository.save(EventJpaEntity.builder()
                    .title("이벤트 제목 " + i)
                    .duration(new DurationJpaVO(startDate, endDate))
                    .eventLink("https://example.com/event/" + i)
                    .build());
        }
        log.info("Board(Notice, Inquiry, Event) 데이터 생성 완료.");
    }
}