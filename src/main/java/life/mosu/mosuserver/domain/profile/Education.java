package life.mosu.mosuserver.domain.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Education {
    ENROLLED("재학생"),
    GRADUATED("졸업생");

    private final String educationName;
}
