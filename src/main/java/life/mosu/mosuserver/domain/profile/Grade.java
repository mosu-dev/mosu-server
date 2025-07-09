package life.mosu.mosuserver.domain.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    HIGH_1("고등학교 1학년"),
    HIGH_2("고등학교 2학년"),
    HIGH_3("고등학교 3학년");

    private final String gradeName;

}
