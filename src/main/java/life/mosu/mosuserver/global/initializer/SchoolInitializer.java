package life.mosu.mosuserver.global.initializer;

import static life.mosu.mosuserver.domain.school.Area.DAECHI;
import static life.mosu.mosuserver.domain.school.Area.NOWON;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import life.mosu.mosuserver.domain.school.AddressJpaVO;
import life.mosu.mosuserver.domain.school.SchoolJpaEntity;
import life.mosu.mosuserver.domain.school.SchoolJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchoolInitializer {

    private final SchoolJpaRepository schoolRepository;

    @PostConstruct
    public void init() {
        if (schoolRepository.count() > 0) {
            log.info("이미 학교 더미 데이터가 존재하여 초기화를 건너뜁니다.");
            return;
        }

        SchoolJpaEntity school1 = SchoolJpaEntity.builder()
                .schoolName("모수고등학교")
                .area(DAECHI)
                .address(new AddressJpaVO("06164", "서울특별시", "강남구 테헤란로 123"))
                .examDate(LocalDate.of(2025, 11, 20))
                .capacity(300L)
                .build();
        schoolRepository.save(school1);

        SchoolJpaEntity school2 = SchoolJpaEntity.builder()
                .schoolName("대치고등학교")
                .area(DAECHI)
                .address(new AddressJpaVO("06283", "서울특별시", "강남구 대치동 학원가 100"))
                .examDate(LocalDate.of(2025, 12, 5))
                .capacity(150L)
                .build();
        schoolRepository.save(school2);

        SchoolJpaEntity school3 = SchoolJpaEntity.builder()
                .schoolName("부산명문고")
                .area(DAECHI)
                .address(new AddressJpaVO("48057", "부산광역시", "해운대구 센텀시티로 50"))
                .examDate(LocalDate.of(2025, 11, 25))
                .capacity(250L)
                .build();
        schoolRepository.save(school3);

        SchoolJpaEntity school4 = SchoolJpaEntity.builder()
                .schoolName("노원스터디센터")
                .area(NOWON)
                .address(new AddressJpaVO("01777", "서울특별시", "노원구 동일로 1400"))
                .examDate(LocalDate.of(2026, 1, 10))
                .capacity(80L)
                .build();
        schoolRepository.save(school4);
    }
}