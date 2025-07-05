package life.mosu.mosuserver.domain.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Subject {

    // 공통 과목
    KOREAN("국어"),
    MATH("수학"),
    ENGLISH("영어"),
    KOREAN_HISTORY("한국사"),

    // 사회탐구
    LIFE_AND_ETHICS("생활과 윤리"),
    ETHICS_AND_IDEOLOGY("윤리와 사상"),
    KOREAN_GEOGRAPHY("한국지리"),
    WORLD_GEOGRAPHY("세계지리"),
    EAST_ASIAN_HISTORY("동아시아사"),
    WORLD_HISTORY("세계사"),
    ECONOMICS("경제"),
    POLITICS_AND_LAW("정치와 법"),
    SOCIETY_AND_CULTURE("사회･문화"),

    // 과학탐구
    PHYSICS_1("물리학Ⅰ"),
    CHEMISTRY_1("화학Ⅰ"),
    BIOLOGY_1("생명과학Ⅰ"),
    EARTH_SCIENCE_1("지구과학Ⅰ"),
    PHYSICS_2("물리학Ⅱ"),
    CHEMISTRY_2("화학Ⅱ"),
    BIOLOGY_2("생명과학Ⅱ"),
    EARTH_SCIENCE_2("지구과학Ⅱ");

    private final String subjectName;
}
