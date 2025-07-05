package life.mosu.mosuserver.domain.event;

import jakarta.persistence.Embeddable;
import life.mosu.mosuserver.infra.storage.domain.File;
import life.mosu.mosuserver.infra.storage.domain.Visibility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventImage extends File {

    @Builder
    public EventImage(String fileName, String s3Key, Visibility visibility) {
        super(fileName, s3Key, visibility);
    }
}
