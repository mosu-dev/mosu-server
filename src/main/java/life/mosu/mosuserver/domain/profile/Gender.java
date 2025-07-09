package life.mosu.mosuserver.domain.profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남자"),
    FEMALE("여자"),
    PENDING("미정");

    private final String genderName;

}
