package life.mosu.mosuserver.domain.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Lunch {
    NONE("선택 안 함"),
    OPTION1("도시락 A"),
    OPTION2("도시락 B"),
    OPTION3("비건 도시락"),
    OPTION4("한식 도시락"),
    OPTION5("양식 도시락"),
    OPTION6("중식 도시락");

    private final String lunchName;
}
//임의 구현
